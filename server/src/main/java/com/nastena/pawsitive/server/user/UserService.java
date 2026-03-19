package com.nastena.pawsitive.server.user;

import com.nastena.pawsitive.dto.ErrorCode;
import com.nastena.pawsitive.server.account.Account;
import com.nastena.pawsitive.server.exceptions.ServerRuntimeException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserService {
    private final UserRepository userRepository;
    private static final Pattern NAME_REGEX = Pattern.compile("^[A-Za-zА-Яа-я\\s]{2,50}$");


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUserOrThrow(Account account, String name) {
        validateNameOrThrow(name);

        User user = new User();
        user.setAccount(account);
        user.setName(name);

        return userRepository.save(user);
    }

    public User getUserOrThrow(Account account) {
        return userRepository
                .findByAccount(account)
                .orElseThrow(() -> new ServerRuntimeException(
                        String.format("No user found for account with email %s", account.getEmail()),
                        ErrorCode.UNAUTHORIZED
                ));
    }


    private void validateNameOrThrow(String name) throws ServerRuntimeException {
        name = name.trim();

        if (name.isBlank()) {
            throw new ServerRuntimeException("Name is blank", ErrorCode.REGISTER_CREDENTIALS_INVALID);
        }

        if (!NAME_REGEX.matcher(name).matches()) {
            throw new ServerRuntimeException("Invalid name format", ErrorCode.REGISTER_CREDENTIALS_INVALID);
        }
    }
}
