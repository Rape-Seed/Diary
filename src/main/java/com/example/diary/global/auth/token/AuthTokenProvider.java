package com.example.diary.global.auth.token;

import java.util.Base64;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthTokenProvider {

    private static final String AUTH_KEY = "role";

    private final String key;

    public AuthTokenProvider(String secret) {
        this.key = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public AuthToken createAuthToken(String id, Date expiry) {
        return new AuthToken(id, expiry, key);
    }

    public AuthToken createAuthToken(String id, String role, Date expiry) {
        return new AuthToken(id, role, expiry, key);
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }


}
