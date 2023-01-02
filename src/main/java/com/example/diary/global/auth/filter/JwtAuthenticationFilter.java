package com.example.diary.global.auth.filter;

import com.example.diary.global.advice.exception.TokenValidFailedException;
import com.example.diary.global.auth.token.AuthToken;
import com.example.diary.global.auth.token.AuthTokenProvider;
import com.example.diary.global.redis.RedisService;
import com.example.diary.global.utils.HeaderUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthTokenProvider tokenProvider;
    private final RedisService redisService;

//    private static final String HEADER_STRING = "X-AUTH-TOKEN";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String accessToken = HeaderUtil.getAccessToken(request);
        if (ObjectUtils.isEmpty(accessToken) || accessToken.equals("")) {
            log.info("[INFO] Couldn't find Token, Permit All Access");
        } else {
            if (redisService.findBlackList(accessToken) != null) {
                log.info("[ERROR] This Token isn't Allow Access");
                throw new TokenValidFailedException("[ERROR] This AccessToken is BlackList");
            }
            checkExpiredToken(accessToken);
            setAuthentication(accessToken);
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String accessToken) {
        AuthToken authToken = tokenProvider.convertAuthToken(accessToken);
        if (authToken.validateToken()) {
            SecurityContextHolder.getContext().setAuthentication(tokenProvider.getAuthentication(authToken));
        }
    }

    private void checkExpiredToken(String accessToken) {
        if (tokenProvider.checkTokenExpired(accessToken)) {
            throw new TokenValidFailedException("[ERROR] AccessToken is expired");
        }
    }
}
