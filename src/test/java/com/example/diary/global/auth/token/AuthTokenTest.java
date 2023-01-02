package com.example.diary.global.auth.token;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.diary.domain.member.entity.Role;
import com.example.diary.global.properties.AuthProperties;
import io.jsonwebtoken.Claims;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthTokenTest {

    public static final String AUTH_KEY = "asdf123asdf123asdf123";
    @Autowired
    AuthProperties authProperties;

    @Test
    void createAccessToken() {
        AuthToken token1 = new AuthToken("swchoi1997@naver.com", Role.MEMBER.toString(),
                new Date(new Date().getTime() + authProperties.getAccessTokenExpiry()), AUTH_KEY);

        String token = token1.getToken();

        AuthToken checkToken = new AuthToken(token, "asdf123asdf123asdf123");
        assertThat(checkToken.getExpirationDateFromToken().getTime()).isLessThanOrEqualTo(
                new Date(new Date().getTime() + authProperties.getAccessTokenExpiry()).getTime());
    }

    @Test
    void createRefreshToken() {
        AuthToken token1 = new AuthToken("swchoi1997@naver.com",
                new Date(new Date().getTime() + authProperties.getRefreshTokenExpiry()), AUTH_KEY);

        String token = token1.getToken();

        AuthToken checkToken = new AuthToken(token, "asdf123asdf123asdf123");
        assertThat(checkToken.getExpirationDateFromToken().getTime()).isLessThanOrEqualTo(
                new Date(new Date().getTime() + authProperties.getRefreshTokenExpiry()).getTime());
    }

    @Test
    void getClaimByToken() {
        AuthToken token = new AuthToken("swchoi1997@naver.com", Role.MEMBER.toString(),
                new Date(new Date().getTime() + authProperties.getAccessTokenExpiry()), AUTH_KEY);
        Claims tokenClaims = token.getTokenClaims();
        assertThat(tokenClaims.getSubject()).isEqualTo("swchoi1997@naver.com");
        assertThat(tokenClaims.get("role")).isEqualTo(Role.MEMBER.toString());
        assertThat(tokenClaims.getExpiration()).isBefore(
                Instant.ofEpochSecond(new Date().getTime() + authProperties.getAccessTokenExpiry()));
    }

    @Test
    void checkTokenValid() {
        AuthToken token = new AuthToken("swchoi1997@naver.com", Role.MEMBER.toString(),
                new Date(new Date().getTime() + authProperties.getAccessTokenExpiry()), AUTH_KEY);
        assertTrue(token.validateToken());
    }

    @Test
    void checkTokenUnValid() {
        AuthToken token = new AuthToken(null, null);
        assertFalse(token.validateToken());
    }

    @Test
    void getExpiryDateByAccessToken() {
        long current = new Date().getTime();

        AuthToken token = new AuthToken("swchoi1997@naver.com", Role.MEMBER.toString(),
                new Date(current + authProperties.getAccessTokenExpiry()), AUTH_KEY);
        LocalDateTime tokenTime = LocalDateTime.ofInstant(
                token.getExpirationDateFromToken().toInstant(), ZoneId.systemDefault());
        assertThat(tokenTime.getHour()).isEqualTo(LocalDateTime.now().getHour());
        assertThat(tokenTime.getMinute()).isEqualTo(LocalDateTime.now().plusMinutes(30).getMinute());
    }
}