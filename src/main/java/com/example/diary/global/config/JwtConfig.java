//package com.example.diary.global.config;
//
//import com.example.diary.global.auth.token.AuthTokenProvider;
//import java.util.Base64;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class JwtConfig {
//
//    @Value("${spring.jwt.secretKey}")
//    private String secretKey;
//
//    @Bean
//    public AuthTokenProvider authTokenProvider() {
//        return new AuthTokenProvider(Base64.getEncoder().encodeToString(secretKey.getBytes()));
//    }
//}
