package com.leaderboard.api_gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;

@RequiredArgsConstructor
@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
    private SecretKey getSigningKey() {
        // Try Base64 first, fallback to raw bytes
        try {
            byte[] keyBytes = Base64.getDecoder().decode(secret);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (IllegalArgumentException e) {
            // Not Base64, treat as raw string
            return Keys.hmacShaKeyFor(secret.getBytes());
        }
    }
    private Claims getClaims(String token ){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public boolean validateToken(String token){
        try{
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public JwtUserInfo extractUserInfo(String token){
        Claims claims = getClaims(token);
        Long userId = Long.valueOf(claims.getSubject());
        String email = claims.get("email", String.class);
        String role = claims.get("role", String.class);
        return new JwtUserInfo(
                userId,
                email,
                role
        );
    }
}
