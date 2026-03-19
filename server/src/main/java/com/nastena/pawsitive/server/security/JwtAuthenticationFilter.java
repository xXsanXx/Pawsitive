package com.nastena.pawsitive.server.security;

import com.nastena.pawsitive.dto.ErrorCode;
import com.nastena.pawsitive.server.exceptions.ServerRuntimeException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    @Value("${custom.dev-mode}")
    private Boolean isDevMode;

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        boolean shouldNotFilter = path.equals("/api/account/login") || path.equals("/api/account/register");

        if (isDevMode) {
            shouldNotFilter |= path.startsWith("/api/dev");
        }

        return shouldNotFilter;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException, ServerRuntimeException {
        Optional<String> maybeToken = jwtUtils.tryGetToken(request);

        if (maybeToken.isEmpty()) {
            SecurityContextHolder.clearContext();
            return;
        }

        String token = maybeToken.get();

        if (isDevMode)
            log.info("Read token: {}", token);

        try {
            String email = jwtUtils.getEmailFromTokenOrThrow(token);
            String role = jwtUtils.getRoleFromTokenOrThrow(token);

            if (isDevMode)
                log.info("Checking authentication for {} with role {}", email, role);

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        email, null, List.of(authority)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);

                if (isDevMode)
                    log.info("Authenticated user: {} with role: {}", email, role);
            }

            filterChain.doFilter(request, response);

        } catch (JwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
        }
    }
}
