package com.nastena.pawsitive.server.security;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

import com.nastena.pawsitive.dto.ErrorCode;
import com.nastena.pawsitive.server.exceptions.ServerRuntimeException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
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

    public String getEmailFromTokenOrThrow(String token) throws JwtException {
        Claims claims = validateToken(token);
        return claims.getSubject();
    }

    public String getRoleFromTokenOrThrow(String token) throws JwtException {
        Claims claims = validateToken(token);
        return claims.get("role", String.class);
    }

    public Optional<String> tryGetToken(HttpServletRequest request) throws ServerRuntimeException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            log.error("Header is not valid: {}", header);
            return Optional.empty();
        }

        return Optional.of(header.substring(7));
    }

}
