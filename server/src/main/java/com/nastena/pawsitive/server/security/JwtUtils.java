package com.nastena.pawsitive.server.security;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
    private final Key key;
    private final long expiration = 1000 * 60 * 60 * 24; // 1 day

    public JwtUtils(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public Claims validateToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getEmailFromToken(String token) {
        Claims claims = validateToken(token);
        return claims.getSubject();
    }

    public String getRoleFromToken(String token) {
        Claims claims = validateToken(token);
        return claims.get("role", String.class);
    }


}
