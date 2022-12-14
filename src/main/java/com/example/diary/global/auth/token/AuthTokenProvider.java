package com.example.diary.global.auth.token;

import com.example.diary.global.advice.exception.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Slf4j
@RequiredArgsConstructor
public class AuthTokenProvider {

    private static final String AUTH_KEY = "role";

    private final String key;

    public AuthToken createAuthToken(String id, Date expiry) {
        return new AuthToken(id, expiry, key);
    }

    public AuthToken createAuthToken(String id, String role, Date expiry) {
        return new AuthToken(id, role, expiry, key);
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }

    public boolean checkTokenExpired(String token) {
        return new AuthToken(token, key).isTokenExpired();
    }

    public Long getTokenExpiry(String token) {
        return new AuthToken(token, key).getExpirationDateFromToken().getTime();
    }

    public Authentication getAuthentication(AuthToken authToken) {
        if (authToken.validateToken()) {
            Claims claims = authToken.getTokenClaims();
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(new String[]{claims.get(AUTH_KEY).toString()})
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
            User user = new User(claims.getSubject(), "", authorities);

            return new UsernamePasswordAuthenticationToken(user, authToken, authorities);
        }
        return (Authentication) new TokenValidFailedException();
    }
}
