package com.nastena.pawsitive.server.account;
import com.nastena.pawsitive.server.account.dto.AccountRole;
import com.nastena.pawsitive.server.account.exceptions.UserWithEmailAlreadyExistsException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerOrThrow(String email, String password, AccountRole role) {
        // TODO: add email and password validation (with custom exception being thrown)

        if (accountRepository.findByEmail(email).isPresent()) {
            throw new UserWithEmailAlreadyExistsException();
        }

        String hashed = passwordEncoder.encode(password);
        Account account = new Account(email, hashed, role);
        return accountRepository.save(account);
    }

    public Account loginOrThrow(String email, String password) throws BadCredentialsException {
        return accountRepository.findByEmail(email)
                .filter(a -> passwordEncoder.matches(password, a.getPasswordHash()))
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));
    }


}

