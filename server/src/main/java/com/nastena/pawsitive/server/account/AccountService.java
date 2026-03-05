package com.nastena.pawsitive.server.account;
import com.nastena.pawsitive.server.account.dto.AccountRole;
import com.nastena.pawsitive.server.account.exceptions.InvalidEmailException;
import com.nastena.pawsitive.server.account.exceptions.InvalidPasswordException;
import com.nastena.pawsitive.server.account.exceptions.UserWithEmailAlreadyExistsException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final static Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private final static Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*[A-Z])(?=.*\\d).{6,}$");

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerOrThrow(String email, String password, AccountRole role) {
        email = normalizedEmail(email);
        throwOnInvalidEmail(email);
        throwOnInvalidPassword(password);

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

    public void throwOnInvalidEmail(String email) throws InvalidEmailException {
        if (!EMAIL_REGEX.matcher(email).matches()) {
            throw new InvalidEmailException();
        }
    }

    public void throwOnInvalidPassword(String password) throws InvalidPasswordException {
        if (!PASSWORD_REGEX.matcher(password).matches()) {
            throw new InvalidPasswordException();
        }
    }

    private String normalizedEmail(String email) {
        return email.trim().toLowerCase();
    }


}

