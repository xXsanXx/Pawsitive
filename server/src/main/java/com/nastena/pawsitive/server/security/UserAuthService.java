package com.nastena.pawsitive.server.security;

import com.nastena.pawsitive.server.account.Account;
import com.nastena.pawsitive.server.account.AccountRepository;
import com.nastena.pawsitive.server.user.User;
import com.nastena.pawsitive.server.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

    private final JwtUtils jwtUtils;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public UserAuthService(
            JwtUtils jwtUtils,
            AccountRepository accountRepository,
            UserRepository userRepository
    ) {
        this.jwtUtils = jwtUtils;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public User getUserFromToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtils.getEmailFromToken(token);

        Account account = accountRepository.findByEmail(email)
                .orElseThrow();

        return userRepository.findByAccount(account)
                .orElseThrow();
    }
}
