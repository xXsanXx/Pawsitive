package com.nastena.pawsitive.server.account;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account register(String username, String email, String password, Account.Role role) {
        String hashed = passwordEncoder.encode(password);
        Account account = new Account(username, email, hashed, role);
        return accountRepository.save(account);
    }

    public Account login(String username, String password) {
        return accountRepository.findByUsername(username)
                .filter(a -> passwordEncoder.matches(password, a.getPasswordHash()))
                .orElse(null);
    }


}

