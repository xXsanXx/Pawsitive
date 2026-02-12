package com.nastena.pawsitive.server.account;

import com.nastena.pawsitive.server.security.JwtUtils;
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
    private final JwtUtils jwtUtils;

    public AccountController(AccountService accountService, JwtUtils jwtUtils) {
        this.accountService = accountService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        try {
            String username = body.get("username");
            String email = body.get("email");
            String password = body.get("password");
            Account.Role role = Account.Role.valueOf(body.get("role"));

            Account newAccount = accountService.register(username, email, password, role);
            return ResponseEntity.ok(newAccount);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка регистрации: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        Account account = accountService.login(username, password);

        if (account != null) {
            String token = jwtUtils.generateToken(
                    account.getUsername(),
                    account.getRole().name()
            );
            return ResponseEntity.ok(Map.of("token", token, "role", account.getRole().name()));
        } else {
            return ResponseEntity.status(401).body("Неверный логин или пароль");
        }
    }


}
