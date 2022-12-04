package com.example.diary.global.config;

import com.example.diary.global.auth.token.AuthTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class JwtConfig {

    @Value("${spring.jwt.secretKey}")
    private String secretKey;

    @Bean
    public AuthTokenProvider authTokenProvider() {
        return new AuthTokenProvider(secretKey);
    }
}
