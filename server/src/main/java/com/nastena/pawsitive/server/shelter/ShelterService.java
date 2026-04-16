package com.nastena.pawsitive.server.shelter;

import com.nastena.pawsitive.dto.ErrorCode;
import com.nastena.pawsitive.dto.UpdateShelterProfileRequest;
import com.nastena.pawsitive.server.account.Account;
import com.nastena.pawsitive.server.exceptions.ServerRuntimeException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ShelterService {
    private final ShelterRepository shelterRepository;
    private static final Pattern NAME_REGEX = Pattern.compile("^[A-Za-zА-Яа-я0-9\\s]{2,50}$");
    private static final Pattern PHONE_REGEX = Pattern.compile(
            "^(\\+7|7|8)?[\\s\\-]?\\(?[489][0-9]{2}\\)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$");

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
                        String.format("No shelter found for account with email %s", account.getEmail()),
                        ErrorCode.UNAUTHORIZED
                ));
    }

    public Shelter getShelterOrThrow(Long id) {
        return shelterRepository
                .findById(id)
                .orElseThrow(() -> new ServerRuntimeException(
                        String.format("No shelter found with id %d", id),
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

    public void updateShelterPhoneOrThrow(Shelter shelter, UpdateShelterProfileRequest updateShelterProfileRequest) throws ServerRuntimeException {
        String phone = updateShelterProfileRequest.getPhone().trim();
        if (!phone.isBlank() && !PHONE_REGEX.matcher(phone).matches()) {
            throw new ServerRuntimeException("Invalid phone format", ErrorCode.INVALID_INPUT);
        }

        shelter.setPhone(phone);
        shelter.setAddress(updateShelterProfileRequest.getAddress());
        shelter.setInfo(updateShelterProfileRequest.getInfo());

        shelterRepository.save(shelter);
    }
}
