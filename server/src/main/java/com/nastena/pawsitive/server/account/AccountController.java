package com.nastena.pawsitive.server.account;

import com.nastena.pawsitive.server.account.dto.AccountResponseDto;
import com.nastena.pawsitive.server.security.JwtUtils;
import com.nastena.pawsitive.server.shelter.ShelterService;
import com.nastena.pawsitive.server.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
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
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            String password = body.get("password");
            Account.Role role = Account.Role.valueOf(body.get("role"));

            Account newAccount = accountService.register(email, password, role);
            switch (role) {
                case USER -> userService.createUser(newAccount);
                case SHELTER -> shelterService.createShelter(newAccount);
            }

            return ResponseEntity.ok(newAccount);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка регистрации: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        Account account = accountService.login(email, password);

        if (account != null) {
            String token = jwtUtils.generateToken(
                    account.getEmail(),
                    account.getRole().name()
            );
            return ResponseEntity.ok(
                    new AccountResponseDto(token, account.getRole().name())
            );

        }
        return ResponseEntity.status(401).body("Неверный логин или пароль");
    }
}
