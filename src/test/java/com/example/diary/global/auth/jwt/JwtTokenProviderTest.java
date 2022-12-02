package com.example.diary.global.auth.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtTokenProviderTest {

    private static final String EMAIL = "test123@naver.com";

    JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        this.jwtTokenProvider = new JwtTokenProvider("asf09a8sdf098asdf098sadf098asdf");
    }

    @Test
    void createAccessToken() {
        assertFalse(jwtTokenProvider.createAccessToken(EMAIL).isEmpty());
    }

    @Test
    void createRefreshToken() {
        assertFalse(jwtTokenProvider.createRefreshToken(EMAIL).isEmpty());
    }

    @Test
    void getEmailFromToken() {
        assertEquals(jwtTokenProvider.getEmailFromToken(jwtTokenProvider.createAccessToken(EMAIL)), EMAIL);
    }

    @Test
    void getExpirationDateFromToken() {
        Date expirationToken = jwtTokenProvider.getExpirationDateFromToken(
                jwtTokenProvider.createAccessToken(EMAIL));
        System.out.println(expirationToken);
        assertFalse(expirationToken.before(new Date(System.currentTimeMillis())));

    }

    @Test
    void isTokenExpired() {
        assertFalse(jwtTokenProvider.isTokenExpired(jwtTokenProvider.createAccessToken(EMAIL)));
    }


}