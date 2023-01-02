package com.example.diary.global.auth.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.function.Function;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class AuthToken {

    public static final String CLAIM_NAME = "role";
    private final String token;
    private final String key;

    public AuthToken(String token, String key) {
        this.key = key;
        this.token = token;
    }

    public AuthToken(String id, Date expiry, String key) {
        this.key = key;
        this.token = createRefreshToken(id, expiry);
    }


    public AuthToken(String id, String role, Date expiry, String key) {
        this.key = key;
        this.token = createAccessToken(id, role, expiry);
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

    public Claims getTokenClaims() {
        try {
            return getAllClaimsFromToken(token);
        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
        }
        return null;
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public boolean validateToken() {
        return getTokenClaims() != null;
    }

    public String getIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Boolean isTokenExpired() {
        final Date expiration = getExpirationDateFromToken();
        return expiration.before(new Date(System.currentTimeMillis()));
    }

    public Date getExpirationDateFromToken() {
        return getClaimFromToken(this.token, Claims::getExpiration);
    }


}
