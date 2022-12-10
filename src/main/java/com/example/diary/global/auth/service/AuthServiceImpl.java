package com.example.diary.global.auth.service;

import static com.example.diary.global.auth.repository.OAuth2AuthorizationRequestCookieRepository.REFRESH_TOKEN;
import static com.example.diary.global.redis.RedisService.BLACKLIST;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.member.entity.Role;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.global.advice.exception.MemberNotFoundException;
import com.example.diary.global.advice.exception.TokenValidFailedException;
import com.example.diary.global.auth.dto.TokenResponseDto;
import com.example.diary.global.auth.token.AuthToken;
import com.example.diary.global.auth.token.AuthTokenProvider;
import com.example.diary.global.common.dto.ResponseDto;
import com.example.diary.global.properties.AuthProperties;
import com.example.diary.global.redis.RedisService;
import com.example.diary.global.utils.CookieUtil;
import com.example.diary.global.utils.HeaderUtil;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final static long THREE_DAYS = 259200000;

    private final RedisService redisService;
    private final AuthTokenProvider authTokenProvider;
    private final AuthProperties authProperties;
    private final MemberRepository memberRepository;


    @Override
    @Transactional
    public ResponseDto<?> refreshToken(HttpServletRequest request, HttpServletResponse response, Member member) {
        AuthToken authAccessToken = authTokenProvider.convertAuthToken(HeaderUtil.getAccessToken(request));
        ResponseDto<?> checkAccessToken = checkTokenStatus(authAccessToken);
        if (checkAccessToken != null) {
            return checkAccessToken;
        }
        String refreshToken = CookieUtil.getToken(request, REFRESH_TOKEN);
        ResponseDto<?> checkRefreshToken = checkTokenStatus(authTokenProvider.convertAuthToken(refreshToken));
        if (checkRefreshToken != null) {
            return checkRefreshToken;
        }

        String emailFromRefreshToken = redisService.getData(member.getEmail());
        ResponseDto<?> checkEmail = checkValidMember(emailFromRefreshToken);
        if (checkEmail != null) {
            return checkEmail;
        }

        String newAccessToken = createAccessToken(emailFromRefreshToken);
        String updateRefreshToken = updateRefreshToken(refreshToken, emailFromRefreshToken);

        CookieUtil.addAccessTokenByCookie(request, response, newAccessToken, authProperties);
        CookieUtil.addRefreshTokenByCookie(request, response, updateRefreshToken, authProperties);

        return new ResponseDto<>(new TokenResponseDto(newAccessToken, updateRefreshToken),
                "AccessToken update Success", HttpStatus.OK);
    }

    private String updateRefreshToken(String refreshToken, String emailFromRefreshToken) {
        if (authTokenProvider.getTokenExpiry(refreshToken) <= THREE_DAYS) {
            redisService.setDataWithExpiration(BLACKLIST + refreshToken.substring(0, 10),
                    refreshToken,
                    authTokenProvider.getTokenExpiry(refreshToken));

            redisService.deleteData(emailFromRefreshToken);

            String newRefreshToken = createRefreshToken(emailFromRefreshToken);
            redisService.setDataWithExpiration(
                    emailFromRefreshToken,
                    newRefreshToken,
                    new Date().getTime() + authProperties.getRefreshTokenExpiry());
            return newRefreshToken;
        }
        return refreshToken;
    }


    private String createAccessToken(String email) {
        AuthToken accessToken = authTokenProvider.createAuthToken(
                email,
                Role.MEMBER.name(),
                new Date(new Date().getTime() + authProperties.getAccessTokenExpiry()));
        return accessToken.getToken();
    }

    private String createRefreshToken(String email) {
        AuthToken accessToken = authTokenProvider.createAuthToken(
                email,
                new Date(new Date().getTime() + authProperties.getRefreshTokenExpiry()));
        return accessToken.getToken();
    }

    private ResponseDto<?> checkTokenStatus(AuthToken authToken) {
        if (authToken.validateToken()) {
            return new ResponseDto<>(new TokenValidFailedException("유요하지 않은 토큰입니다."), HttpStatus.FORBIDDEN);
        }
        if (!new Date(System.currentTimeMillis()).before(authToken.getExpirationDateFromToken())) {
            return new ResponseDto<>(new IllegalArgumentException("토큰의 유요시간이 만료되지 않았습니다."), HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    private ResponseDto<?> checkValidMember(String email) {
        if (email == null) {
            return new ResponseDto<>(new TokenValidFailedException("유요하지 않은 토큰입니다."), HttpStatus.FORBIDDEN);
        }
        if (memberRepository.findByEmail(email) == null) {
            return new ResponseDto<>(new MemberNotFoundException("가입되지 않은 사용자입니다."), HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
