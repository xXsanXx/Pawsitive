package com.nastena.pawsitive.server.animal;

import com.nastena.pawsitive.dto.CreateAnimalRequest;
import com.nastena.pawsitive.dto.ErrorCode;
import com.nastena.pawsitive.server.exceptions.ServerRuntimeException;
import com.nastena.pawsitive.server.shelter.Shelter;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;

    private static final Pattern NAME_REGEX = Pattern.compile("^[A-Za-zА-Яа-я\\s]{2,50}$");


    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public Animal createAnimalOrThrow(Shelter shelter, CreateAnimalRequest createAnimalRequest) {
        Animal animal = new Animal(shelter);


        String name = createAnimalRequest.getName().trim();
        validateNameOrThrow(name);


        return animalRepository.save(animal);
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


}
