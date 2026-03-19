package com.nastena.pawsitive.server.account;

import com.nastena.pawsitive.dto.*;
import com.nastena.pawsitive.server.security.JwtUtils;
import com.nastena.pawsitive.server.shelter.ShelterService;
import com.nastena.pawsitive.server.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;
    private final ShelterService shelterService;
    private final JwtUtils jwtUtils;

    @Value("${custom.dev-mode}")
    private Boolean isDevMode;

    public AccountController(AccountService accountService, UserService userService, ShelterService shelterService, JwtUtils jwtUtils) {
        this.accountService = accountService;
        this.userService = userService;
        this.shelterService = shelterService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        String name = registerRequest.getName();
        String email = registerRequest.getEmail();
        String password = registerRequest.getPassword();
        AccountRole role = registerRequest.getRole();

        if (isDevMode)
            log.info(" [register] name: {}, email: {}, password: {}, role: {}", name, email, password, role.name());

        Account newAccount = accountService.registerOrThrow(email, password, role);
        switch (role) {
            case USER -> userService.createUserOrThrow(newAccount, name);
            case SHELTER -> shelterService.createShelter(newAccount);
        }
        return ResponseEntity.ok("Регистрация успешна!");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        if (isDevMode)
            log.info(" [login request] email: {}, password: {}", email, password);

        Account account = accountService.getAccountOrThrow(email, password);

        String token = jwtUtils.generateToken(
                account.getEmail(),
                account.getRole().name()
        );

        if (isDevMode)
            log.info(" [login request] successfully logged in with token {}", token);

        return ResponseEntity.ok(
                new LoginResponse(token, account.getRole())
        );
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponse> me(Authentication authentication) {
        String role = authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority()
                .replace("ROLE_", "");

        if (isDevMode)
            log.info("[me] Got role: {}", role);

        return ResponseEntity.ok(new MeResponse(AccountRole.valueOf(role)));
    }

}
