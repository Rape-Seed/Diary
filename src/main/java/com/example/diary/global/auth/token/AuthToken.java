package com.example.diary.global.auth.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthToken {

    public static final String CLAIM_NAME = "role";
    private final String token;
    private final String key;

    public AuthToken(String id, Date expiry, String key) {
        this.token = createRefreshToken(id, expiry);
        this.key = key;
    }

    public AuthToken(String id, String role, Date expiry, String key) {
        this.token = createAccessToken(id, role, expiry);
        this.key = key;
    }

    private String createAccessToken(String id, String role, Date expiry) {
        return Jwts.builder()
                .setSubject(id)
                .claim(CLAIM_NAME, role)
                .signWith(SignatureAlgorithm.HS512, this.key)
                .setExpiration(expiry)
                .compact();
    }

    private String createRefreshToken(String id, Date expiry) {
        return Jwts.builder()
                .setSubject(id)
                .signWith(SignatureAlgorithm.HS512, this.key)
                .setExpiration(expiry)
                .compact();
    }

    public boolean validate() {
        return getTokenClaims() != null;
    }

    public Claims getTokenClaims() {

    }

}
