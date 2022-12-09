package com.example.diary.global.properties;

import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthProperties {

    @Value("${spring.jwt.secretKey}")
    private String secretKey;

    @Value("${app.auth.accessTokenExpiry}")
    private Long accessTokenExpiry;

    @Value("${app.auth.refreshTokenExpiry}")
    private Long refreshTokenExpiry;

    public String getSecretKey() {
        return Base64.getEncoder().encodeToString(this.secretKey.getBytes());
    }

    public Long getAccessTokenExpiry() {
        return accessTokenExpiry;
    }

    public Long getRefreshTokenExpiry() {
        return refreshTokenExpiry;
    }
}
