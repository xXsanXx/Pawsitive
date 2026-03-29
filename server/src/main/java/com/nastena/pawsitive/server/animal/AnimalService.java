package com.nastena.pawsitive.server.animal;

import com.nastena.pawsitive.dto.*;
import com.nastena.pawsitive.server.exceptions.ServerRuntimeException;
import com.nastena.pawsitive.server.shelter.Shelter;
import com.nastena.pawsitive.utils.AnimalUtils;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.regex.Pattern;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;

    private static final Pattern NAME_REGEX = Pattern.compile("^[A-Za-zА-Яа-я\\s]{2,50}$");


    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public Animal createAnimalOrThrow(Shelter shelter, CreateAnimalRequest createAnimalRequest) {
        String name = createAnimalRequest.getName().trim();
        validateNameOrThrow(name);

        AnimalType type = createAnimalRequest.getType();
        AnimalBreed breed = createAnimalRequest.getBreed();
        validateBreedOrThrow(type, breed);

        Long birthDate = createAnimalRequest.getBirthDate();
        validateBirthDateOrThrow(birthDate);

        AnimalGender gender = createAnimalRequest.getGender();
        String description = createAnimalRequest.getDescription();

        Animal animal = new Animal(shelter);
        animal.setName(name);
        animal.setType(type);
        animal.setBreed(breed);
        animal.setBirthDate(birthDate);
        animal.setGender(gender);
        animal.setDescription(description);

        return animalRepository.save(animal);
    }

    public Animal updateAnimalOrThrow(UpdateAnimalRequest updateAnimalRequest) {
        Animal animal = animalRepository.findById(updateAnimalRequest.getId()).orElseThrow(() -> new ServerRuntimeException("Can not find animal by id", ErrorCode.INVALID_INPUT));

        String name = updateAnimalRequest.getName().trim();
        validateNameOrThrow(name);

        AnimalBreed breed = updateAnimalRequest.getBreed();
        validateBreedOrThrow(animal.getType(), breed);

        Long birthDate = updateAnimalRequest.getBirthDate();
        validateBirthDateOrThrow(birthDate);

        AnimalGender gender = updateAnimalRequest.getGender();
        String description = updateAnimalRequest.getDescription();

        animal.setName(name);
        animal.setBreed(breed);
        animal.setBirthDate(birthDate);
        animal.setGender(gender);
        animal.setDescription(description);


        return animalRepository.save(animal);
    }

    public void removeAnimalOrThrow(Long id) {
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new ServerRuntimeException("Can not find animal by id", ErrorCode.INVALID_INPUT));
        animalRepository.delete(animal);
    }

    private void validateNameOrThrow(String name) throws ServerRuntimeException {
        name = name.trim();

        if (name.isBlank()) {
            throw new ServerRuntimeException("Animal name is blank", ErrorCode.INVALID_INPUT);
        }

        if (!NAME_REGEX.matcher(name).matches()) {
            throw new ServerRuntimeException("Invalid animal name format", ErrorCode.INVALID_INPUT);
        }
    }

    private void validateBreedOrThrow(AnimalType type, AnimalBreed breed) throws ServerRuntimeException {
        Set<AnimalBreed> breeds = new AnimalUtils().getBreedForAnimalType(type);

        if (!breeds.contains(breed)) {
            throw new ServerRuntimeException("Invalid animal breed", ErrorCode.INVALID_INPUT);
        }
    }

    private void validateBirthDateOrThrow(Long birthDate) throws ServerRuntimeException {
        long now = System.currentTimeMillis();

        if (birthDate > now) {
            throw new ServerRuntimeException("Invalid animal birth date", ErrorCode.INVALID_INPUT);
        }

    }

}
