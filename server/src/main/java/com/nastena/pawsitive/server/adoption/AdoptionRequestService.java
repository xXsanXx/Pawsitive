package com.nastena.pawsitive.server.adoption;

import com.nastena.pawsitive.dto.AdoptionStatus;
import com.nastena.pawsitive.dto.ErrorCode;
import com.nastena.pawsitive.dto.FormRequest;
import com.nastena.pawsitive.server.animal.Animal;
import com.nastena.pawsitive.server.animal.AnimalService;
import com.nastena.pawsitive.server.exceptions.ServerRuntimeException;
import com.nastena.pawsitive.server.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AdoptionRequestService {

    private final AdoptionRequestRepository repository;
    private final AnimalService animalService;

    private static final Pattern NAME_REGEX = Pattern.compile("^[A-Za-zА-Яа-я0-9\\s]{2,200}$");
    private static final Pattern PHONE_REGEX = Pattern.compile(
            "^(\\+7|7|8)?[\\s\\-]?\\(?[489][0-9]{2}\\)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$");

    public void createFormRequest(User user, Long id, FormRequest request) {

        Animal animal = animalService.getAnimalOrThrow(id);

        AdoptionRequest adoptionRequest = new AdoptionRequest();
        adoptionRequest.setUser(user);
        adoptionRequest.setAnimal(animal);
        adoptionRequest.setFullName(request.getFullName());
        adoptionRequest.setAge(request.getAge());
        adoptionRequest.setProfession(request.getProfession());
        adoptionRequest.setPhone(request.getPhone());
        adoptionRequest.setStatus(AdoptionStatus.PENDING);

        repository.save(adoptionRequest);
    }

    public void validateFullNameOrThrow(String fullName) throws ServerRuntimeException {
        fullName = fullName.trim();

        if (fullName.isBlank()) {
            throw new ServerRuntimeException("Full name is blank", ErrorCode.FORM_CREDENTIALS_INVALID);
        }

        if (!NAME_REGEX.matcher(fullName).matches()) {
            throw new ServerRuntimeException("Invalid full name format", ErrorCode.FORM_CREDENTIALS_INVALID);
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
}
