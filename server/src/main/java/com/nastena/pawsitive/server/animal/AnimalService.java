package com.nastena.pawsitive.server.animal;

import com.nastena.pawsitive.dto.AnimalRequest;
import com.nastena.pawsitive.dto.AnimalResponse;
import com.nastena.pawsitive.server.shelter.Shelter;
import org.springframework.stereotype.Service;
import com.nastena.pawsitive.server.security.ShelterAuthService;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public Animal createAnimal(Shelter shelter) {
        Animal animal = new Animal(shelter);
        return animalRepository.save(animal);
    }

    public Optional<Animal> getAnimalById(Animal animal) {
        return animalRepository.findByAnimal(animal);
    }

    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

}
