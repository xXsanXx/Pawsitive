package com.nastena.pawsitive.server.debug;

import com.nastena.pawsitive.dto.AccountRole;
import com.nastena.pawsitive.server.security.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@ConditionalOnProperty(value = "custom.dev-mode", havingValue = "true")
public class DevJwtFilter extends OncePerRequestFilter {

    @Value("${custom.dev-mode-user-token}")
    private String userToken;

    @Value("${custom.dev-mode-shelter-token}")
    private String shelterToken;

    @Value("${custom.dev-mode-user-email}")
    private String userEmail;

    @Value("${custom.dev-mode-shelter-email}")
    private String shelterEmail;

    private final OncePerRequestFilter jwtFilter;
    private final JwtUtils jwtUtils;

    public DevJwtFilter(OncePerRequestFilter jwtFilter, JwtUtils jwtUtils) {
        this.jwtFilter = jwtFilter;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        Optional<String> maybeToken = jwtUtils.tryGetToken(request);

        if (maybeToken.isEmpty()) {
            jwtFilter.doFilter(request, response, filterChain);
            return;
        }

        String token = maybeToken.get().trim();
        log.info("Checking token for dev-mode: {} | USER DEV TOKEN {} | SHELTER DEV TOKEN {}", token, userToken, shelterToken);

        String devEmail = null;
        AccountRole role = null;
        if (token.equals(userToken)) {
            devEmail = userEmail;
            role = AccountRole.USER;
        }
        else if (token.equals(shelterToken)) {
            devEmail = shelterEmail;
            role = AccountRole.SHELTER;
        }

        if (devEmail == null) {
            jwtFilter.doFilter(request, response, filterChain);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    devEmail, null, List.of(authority)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("Authenticated dev-mode user: {} with role: {}", devEmail, role);
        }

        filterChain.doFilter(request, response);
    }
}
