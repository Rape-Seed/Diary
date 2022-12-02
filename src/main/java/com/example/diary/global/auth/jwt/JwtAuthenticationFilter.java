package com.example.diary.global.auth.jwt;

import static com.example.diary.global.redis.RedisKey.ACCESS;
import static com.example.diary.global.redis.RedisKey.BLACKLIST;

import com.example.diary.global.auth.member.MemberDetailsService;
import com.example.diary.global.redis.RedisService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
//@Configuration
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDetailsService memberDetailsService;
    private final RedisService redisService;

    private String HEADER_STRING = "X-AUTH-TOKEN";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String header = request.getHeader(HEADER_STRING); // header에서 X-AUTH-TOKEN 가져옴
        final String accessToken = validateAccessToken(header);
        final String redisAccessToken = redisService.getData(BLACKLIST.getKey() + accessToken);

        if (redisAccessToken == null) {
            setAuthentication(request, accessToken);
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(HttpServletRequest request, String accessToken) {
        if (jwtTokenProvider.validateToken(accessToken)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            String email = jwtTokenProvider.getEmailFromToken(accessToken);
            UserDetails userDetails = memberDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null, null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            log.info("Authenticated user " + email + ", setting security context");
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private String validateAccessToken(String header) {
        String accessToken = null;
        if (header != null && header.startsWith(ACCESS.getKey())) {
            accessToken = header.replace(ACCESS.getKey(), "");
        }
        if (ObjectUtils.isEmpty(accessToken)) {
            log.warn("[WARN] couldn't find bearer string, will ignore the header");
        }
        checkExpiredToken(accessToken);
        return accessToken;
    }

    private void checkExpiredToken(String accessToken) {
        if (jwtTokenProvider.isTokenExpired(accessToken)) {
            throw new JwtException("[ERROR] AccessToken is expired");
        }
    }
}
