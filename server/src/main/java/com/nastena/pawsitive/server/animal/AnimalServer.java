package com.nastena.pawsitive.server.animal;

import com.nastena.pawsitive.server.shelter.Shelter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalServer {
    private final AnimalRepository animalRepository;

    public AnimalServer(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
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
}
