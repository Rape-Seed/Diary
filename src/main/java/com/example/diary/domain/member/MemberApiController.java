package com.example.diary.domain.member;

import com.example.diary.global.auth.token.AuthTokenProvider;
import com.example.diary.global.properties.AuthProperties;
import com.example.diary.global.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberApiController {

    private final AuthTokenProvider tokenProvider;
    private final RedisService redisService;
//    private final AppProperties appProperties;

    private final AuthProperties authProperties;
    //    private final OAuth2UserInfo oAuth2UserInfo;
    private final AuthenticationManager authenticationManager;

//    @PostMapping("/v1/sign")
//    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response,
//                                   @RequestBody LoginRequestDto loginRequestDto) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginRequestDto.getEmail(), loginRequestDto.getPassword()
//                )
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        AuthToken accessToken = tokenProvider.createAuthToken(loginRequestDto.getEmail(),
//                Role.MEMBER.name(),
//                new Date(new Date().getTime() + authProperties.getAccessTokenExpiry()));
//
//        long refreshTokenExpiry = new Date().getTime() + authProperties.getRefreshTokenExpiry();
//        AuthToken refreshToken = tokenProvider.createAuthToken(loginRequestDto.getEmail(),
//                new Date(refreshTokenExpiry));
//
//        String redisRefreshToken = redisService.getData(loginRequestDto.getEmail());
//        if (redisRefreshToken != null) {
//            redisService.setDataWithExpiration("Anonymous", redisRefreshToken,
//                    tokenProvider.getTokenExpiry(redisRefreshToken));
//            redisService.deleteData(loginRequestDto.getEmail());
//        }
//        redisService.setDataWithExpiration(loginRequestDto.getEmail(), refreshToken.getToken(), refreshTokenExpiry);
//
//        int cookieMaxAge = (int) refreshTokenExpiry / 60;
//        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
//        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);
//
//        return new ResponseEntity<>(
//                new LoginResponseDto(loginRequestDto.getEmail(), accessToken.getToken(), refreshToken.getToken()),
//                HttpStatus.OK);
//    }
}
