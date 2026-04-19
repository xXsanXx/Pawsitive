package com.nastena.pawsitive.server.user;

import com.nastena.pawsitive.dto.ErrorCode;
import com.nastena.pawsitive.dto.UserFormUpdateRequest;
import com.nastena.pawsitive.server.account.Account;
import com.nastena.pawsitive.server.exceptions.ServerRuntimeException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserService {
    private final UserRepository userRepository;
    private static final Pattern NAME_REGEX = Pattern.compile("^[А-Яа-я\\s]{2,300}$");

    private static final Pattern PHONE_REGEX = Pattern.compile(
            "^(\\+7|7|8)?[\\s\\-]?\\(?[489][0-9]{2}\\)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$");


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUserOrThrow(Account account, String name) {
        name = name.trim();
        validateUserNameOrThrow(name);

        User user = new User();
        user.setAccount(account);
        user.setName(name);


        return userRepository.save(user);
    }

    public void updateFormOrThrow(User user, UserFormUpdateRequest userFormUpdateRequest) {
        String name = userFormUpdateRequest.getName().trim();
        validateUserNameOrThrow(name);

        Long birthDate = userFormUpdateRequest.getBirthDate();
        validateBirthDateOrThrow(birthDate);

        String phone = userFormUpdateRequest.getPhone().trim();
        validatePhoneOrThrow(phone);

        user.setName(name);
        user.setBirthDate(birthDate);
        user.setPhone(phone);
        user.setProfession(userFormUpdateRequest.getProfession().trim());

        userRepository.save(user);
    }

    public User getUserOrThrow(Account account) {
        return userRepository
                .findByAccount(account)
                .orElseThrow(() -> new ServerRuntimeException(
                        String.format("No user found for account with email %s", account.getEmail()),
                        ErrorCode.UNAUTHORIZED
                ));
    }

    public void validateUserNameOrThrow(String name) throws ServerRuntimeException {
        name = name.trim();

        if (name.isBlank()) {
            throw new ServerRuntimeException("Name is blank", ErrorCode.REGISTER_CREDENTIALS_INVALID);
        }

        if (!NAME_REGEX.matcher(name).matches()) {
            throw new ServerRuntimeException("Invalid name format", ErrorCode.REGISTER_CREDENTIALS_INVALID);
        }
    }

    public void validatePhoneOrThrow(String phone) throws ServerRuntimeException {
        phone = phone.trim();

        if (phone.isBlank()) {
            throw new ServerRuntimeException("Phone is blank", ErrorCode.FORM_CREDENTIALS_INVALID);
        }

        if (!PHONE_REGEX.matcher(phone).matches()) {
            throw new ServerRuntimeException("Invalid phone format", ErrorCode.FORM_CREDENTIALS_INVALID);
        }
    }

    private void validateBirthDateOrThrow(Long birthDate) throws ServerRuntimeException {
        long now = System.currentTimeMillis();

        if (birthDate > now) {
            throw new ServerRuntimeException("Invalid user birth date", ErrorCode.INVALID_INPUT);
        }

    }
}
