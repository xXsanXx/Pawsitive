package com.nastena.pawsitive.server.animal;

import com.nastena.pawsitive.server.animal.dto.AnimalRequestDto;
import com.nastena.pawsitive.server.animal.dto.AnimalResponseDto;
import com.nastena.pawsitive.server.shelter.Shelter;
import com.nastena.pawsitive.server.shelter.ShelterService;
import org.springframework.stereotype.Service;
import com.nastena.pawsitive.server.security.ShelterAuthService;
import java.util.List;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final ShelterAuthService shelterAuthService;

    public AnimalService(AnimalRepository animalRepository, ShelterAuthService shelterAuthService) {
        this.animalRepository = animalRepository;
        this.shelterAuthService = shelterAuthService;
    }

    public Animal createAnimal(Shelter shelter) {
        Animal animal = new Animal(shelter);
        return animalRepository.save(animal);
    }

    public List<Animal> getAnimalsByShelter(Long shelterId) {
        return animalRepository.findByShelterId(shelterId);
    }

    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    public List<Animal> getAnimalWithFilters(
            String type,
            Animal.Gender gender,
            Integer minAge,
            Integer maxAge
    ) {
        return animalRepository.findWithFilters(type, gender, minAge, maxAge);
    }

    public AnimalResponseDto addAnimal(
            AnimalRequestDto dto,
            String authHeader
    ) {

        Shelter shelter = shelterAuthService.getShelterFromToken(authHeader);

        Animal animal = new Animal();
        animal.setAnimalName(dto.getName());
        animal.setType(dto.getType());
        animal.setBreed(dto.getBreed());
        animal.setAge(dto.getAge());
        animal.setGender(Animal.Gender.valueOf(dto.getGender()));
        animal.setHealthInfo(dto.getHealthInfo());
        animal.setShelter(shelter);

        animalRepository.save(animal);

        return new AnimalResponseDto(animal);
    }
}
