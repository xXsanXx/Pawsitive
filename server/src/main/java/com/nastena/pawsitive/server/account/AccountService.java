package com.nastena.pawsitive.server.account;

import com.nastena.pawsitive.dto.AccountRole;
import com.nastena.pawsitive.dto.ErrorCode;
import com.nastena.pawsitive.server.exceptions.ServerRuntimeException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final static Pattern EMAIL_REGEX = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
    );
    private final static Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*[A-Z])(?=.*\\d).{12,}$");

    private static final Pattern NAME_REGEX = Pattern.compile("^[A-Za-zА-Яа-я\\s]{2,50}$");

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerOrThrow(String name, String email, String password, AccountRole role) throws ServerRuntimeException {
        name = name.trim();
        email = email.trim();
        password = password.trim();

        checkCredentialsOrThrow(name, email, password);


        if (accountRepository.findByEmail(email).isPresent()) {
            throw new ServerRuntimeException(ErrorCode.USER_ALREADY_EXISTS);
        }

        String hashed = passwordEncoder.encode(password);
        Account account = new Account(email, hashed, role);
        return accountRepository.save(account);
    }

    public Account getAccountOrThrow(String email, String password) throws ServerRuntimeException {
        String trimmedEmail = email.trim();
        String trimmedPassword = password.trim();

        if (trimmedEmail.isBlank() || trimmedPassword.isBlank()) {
            throw new ServerRuntimeException("Email or password is blank!", ErrorCode.LOGIN_CREDENTIALS_INVALID);
        }

        return accountRepository.findByEmail(email)
                .filter(a -> passwordEncoder.matches(password, a.getPasswordHash()))
                .orElseThrow(() -> new ServerRuntimeException("Credentials do not match!", ErrorCode.LOGIN_CREDENTIALS_INVALID));
    }



    private void checkCredentialsOrThrow(String name, String email, String password) throws ServerRuntimeException {
        String trimmedName = name.trim();

        if (trimmedName.isBlank()) {
            throw new ServerRuntimeException("Name is blank!", ErrorCode.LOGIN_CREDENTIALS_INVALID);
        }

        if (!NAME_REGEX.matcher(name).matches()) {
            throw new ServerRuntimeException("Invalid name format", ErrorCode.REGISTER_CREDENTIALS_INVALID);
        }

        if (!EMAIL_REGEX.matcher(email).matches()) {
            throw new ServerRuntimeException("Invalid email format", ErrorCode.REGISTER_CREDENTIALS_INVALID);
        }
        if (!PASSWORD_REGEX.matcher(password).matches()) {
            throw new ServerRuntimeException("Invalid password format", ErrorCode.REGISTER_CREDENTIALS_INVALID);
        }
    }

}

