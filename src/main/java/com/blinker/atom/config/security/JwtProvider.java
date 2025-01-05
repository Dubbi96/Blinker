package com.blinker.atom.config.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;

    private final Duration expiration = Duration.ofDays(14);

    public String createToken(Map<String, Object> claims) {
        Date now = new Date();
        Date expiryDate = Date.from(now.toInstant().plus(expiration));
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(createSignKey(), SignatureAlgorithm.HS512) // 서명 알고리즘 확인
                .compact();
    }

    public Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(createSignKey())
                .build()
                .parseClaimsJws(token.replace("Bearer ", "")) // "Bearer " 제거
                .getBody();
        } catch (ExpiredJwtException e) {
            log.error("토큰이 만료되었습니다: {}", e.getMessage());
            throw new IllegalArgumentException("토큰이 만료되었습니다.");
        } catch (JwtException e) {
            log.error("JWT 파싱 오류: {}", e.getMessage());
            throw new IllegalArgumentException("JWT 토큰 검증에 실패했습니다. token : ");
        }
    }

    private SecretKey createSignKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}