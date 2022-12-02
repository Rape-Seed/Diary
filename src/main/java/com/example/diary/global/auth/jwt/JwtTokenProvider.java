package com.example.diary.global.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Setter
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    public static final long accessTokenValidTime = 5 * 60 * 1000L; // 5분
    public static final long refreshTokenValidTime = 1000L * 60 * 60 * 24 * 7; // 7일

    @Value("${spring.jwt.secretKey}")
    private String secretKey;

    public JwtTokenProvider(String secretKey) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    @PostConstruct
    private void setSecretKeyBase64() {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * @param token
     * @return 토큰으로 부터 얻은 이메일 정보
     */
    public String getEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * @param token
     * @return 토큰으로 부터 얻은 만료 날짜 정보
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // 토큰이 만료되었는지 확인(true - 만료)

    /**
     * @param token
     * @return 토큰이 만료 되었는지 확인 (true - 만료, false - 유효)
     */
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date(System.currentTimeMillis()));
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public Boolean validateToken(String token) {
        try {
            getAllClaimsFromToken(token);
        } catch (IllegalArgumentException e) {
            log.info("[ERROR] An error occur during getting username from token");
            e.printStackTrace();
            return false;
        } catch (SignatureException e) {
            log.info("[ERROR] Authentication Failed. Username or Password not valid.");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @param email
     * @return 이메일정보와 토큰값으로 AccessToken생성
     */
    public String createAccessToken(String email) {
        Claims claims = Jwts.claims().setSubject(email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setSubject(email)
                .signWith(SignatureAlgorithm.HS512, this.secretKey)
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidTime))
                .compact();
    }

    /**
     * @param email
     * @return 토큰값으로 RefreshToken생성
     */
    public String createRefreshToken(String email) {
        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512, this.secretKey)
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidTime))
                .compact();
    }


}
