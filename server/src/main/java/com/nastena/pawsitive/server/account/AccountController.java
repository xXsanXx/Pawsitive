package com.nastena.pawsitive.server.account;

import com.nastena.pawsitive.dto.AccountRole;
import com.nastena.pawsitive.dto.LoginRequest;
import com.nastena.pawsitive.dto.LoginResponse;
import com.nastena.pawsitive.dto.MeResponse;
import com.nastena.pawsitive.server.account.dto.AccountRegisterRequest;
import com.nastena.pawsitive.server.security.JwtUtils;
import com.nastena.pawsitive.server.shelter.ShelterService;
import com.nastena.pawsitive.server.user.UserService;
import lombok.extern.slf4j.Slf4j;
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

    public AccountController(AccountService accountService, UserService userService, ShelterService shelterService, JwtUtils jwtUtils) {
        this.accountService = accountService;
        this.userService = userService;
        this.shelterService = shelterService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AccountRegisterRequest accountRegisterRequest) {
        String email = accountRegisterRequest.email();
        String password = accountRegisterRequest.password();
        AccountRole role = accountRegisterRequest.role();

        log.info(" [register] email: {}, password: {}, role: {}", email, password, role.name());

        Account newAccount = accountService.registerOrThrow(email, password, role);
        switch (role) {
            case USER -> userService.createUser(newAccount);
            case SHELTER -> shelterService.createShelter(newAccount);
        }
        return ResponseEntity.ok("Регистрация успешна!");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        log.info(" [login] email: {}, password: {}", email, password);

        Account account = accountService.getAccountOrThrow(email, password);

        String token = jwtUtils.generateToken(
                account.getEmail(),
                account.getRole().name()
        );
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
        return ResponseEntity.ok(new MeResponse(AccountRole.valueOf(role)));
    }
}
