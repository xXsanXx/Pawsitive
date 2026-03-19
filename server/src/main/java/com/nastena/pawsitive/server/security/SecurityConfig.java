package com.nastena.pawsitive.server.security;


import com.nastena.pawsitive.server.debug.DevJwtFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtFilter;
    private final JwtUtils jwtUtils;

    @Value("${custom.dev-mode}")
    private Boolean isDevMode;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter, JwtUtils jwtUtils) {
        this.jwtFilter = jwtFilter;
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        OncePerRequestFilter jwtFilter = isDevMode ? new DevJwtFilter(this.jwtFilter, jwtUtils) : this.jwtFilter;

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> {
                            if (isDevMode) {
                                log.info("Permitting all for dev-mode");
                                auth = auth.requestMatchers("/api/dev/**").permitAll();
                            }
                            auth
                                    .requestMatchers("/api/account/login").permitAll()
                                    .requestMatchers("/api/account/register").permitAll()
                                    .anyRequest().authenticated();
                        }
                )
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }
}
