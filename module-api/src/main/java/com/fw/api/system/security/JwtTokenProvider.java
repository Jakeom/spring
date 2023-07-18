package com.fw.api.system.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.crypto.SecretKey;

/**
 * JWT 토큰 Provider
 * @author sjpaik
 */
@Slf4j
@Component
public class JwtTokenProvider {

    private static String jwtSecret;
    private static int accessTokenExpiration;
    private static int refreshTokenExpiration;
    private static Key key123;
  
    @Value("${jwt.secret.key}")
    private void setJwtSecret(String jwtSecret) {
        JwtTokenProvider.jwtSecret = jwtSecret;
        key123 = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
    @Value("${jwt.access.token.expiration}")
    private void setAccessTokenExpiration(int accessTokenExpiration) {
        JwtTokenProvider.accessTokenExpiration = accessTokenExpiration;
    }
    @Value("${jwt.refresh.token.expiration}")
    private void setRegreshTokenExpiration(int refreshTokenExpiration) {
        JwtTokenProvider.refreshTokenExpiration = refreshTokenExpiration;
    }


    /**
     * Access Token 생성
     */
    public static String generateAccessToken(Authentication authentication) {
 
        LocalDateTime expireDateTime = LocalDateTime.now().plusMinutes(accessTokenExpiration);
        return Jwts.builder()
                .setSubject((String) authentication.getPrincipal())
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Timestamp.valueOf(expireDateTime))
                .signWith(key123,SignatureAlgorithm.HS512)
                //.signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Refresh Token 생성
     */
    public static String generateRefreshToken(Authentication authentication) {
        LocalDateTime expireDateTime = LocalDateTime.now().plusDays(refreshTokenExpiration);
        return Jwts.builder()
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Timestamp.valueOf(expireDateTime))
                .signWith(key123,SignatureAlgorithm.HS512)
                //.signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * JWT 토큰에서 아이디 추출
     */
    public static String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key123)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /**
     * JWT 토큰 유효성 검사
     */
    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key123).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

}
