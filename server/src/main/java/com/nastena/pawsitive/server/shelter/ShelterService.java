package com.nastena.pawsitive.server.shelter;

import com.nastena.pawsitive.dto.ErrorCode;
import com.nastena.pawsitive.server.account.Account;
import com.nastena.pawsitive.server.exceptions.ServerRuntimeException;
import com.nastena.pawsitive.server.user.User;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ShelterService {
    private final ShelterRepository shelterRepository;
    private static final Pattern NAME_REGEX = Pattern.compile("^[A-Za-zА-Яа-я0-9\\s]{2,50}$");

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    public Shelter createShelterOrThrow(Account account, String name) {
        validateShelterNameOrThrow(name);
        Shelter shelter = new Shelter();
        shelter.setAccount(account);
        shelter.setName(name);
        return shelterRepository.save(shelter);
    }

    public Shelter getShelterOrThrow(Account account) {
        return shelterRepository
                .findByAccount(account)
                .orElseThrow(() -> new ServerRuntimeException(
                        String.format("No user found for account with email %s", account.getEmail()),
                        ErrorCode.UNAUTHORIZED
                ));
    }

    public void validateShelterNameOrThrow(String name) throws ServerRuntimeException {
        name = name.trim();

        if (name.isBlank()) {
            throw new ServerRuntimeException("Name is blank", ErrorCode.REGISTER_CREDENTIALS_INVALID);
        }

        if (!NAME_REGEX.matcher(name).matches()) {
            throw new ServerRuntimeException("Invalid name format", ErrorCode.REGISTER_CREDENTIALS_INVALID);
        }
    }
}
