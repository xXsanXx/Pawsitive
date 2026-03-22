package com.nastena.pawsitive.server.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.equals("/api/account/login") ||
                path.equals("/api/account/register");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            log.error("Header is not valid: {}", header);
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;
        }


        String token = header.substring(7);
        log.error("Token: {}", token);


        boolean isValidToken;
        String email = "";
        String role = "";

        try {
            email = jwtUtils.getEmailFromTokenOrThrow(token);
            role = jwtUtils.getRoleFromTokenOrThrow(token);
            isValidToken = true;
        } catch (JwtException e) {
            isValidToken = false;
        }

        if (!isValidToken) {
            log.error("Token is not valid: {}", token);

            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;
        }
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            List.of(authority)
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            log.error("Auth exists");

        }

        filterChain.doFilter(request, response);
    }
}
