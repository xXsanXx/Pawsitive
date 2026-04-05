package com.nastena.pawsitive.server.animal;

import com.nastena.pawsitive.dto.*;
import com.nastena.pawsitive.server.exceptions.ServerRuntimeException;
import com.nastena.pawsitive.server.files.FileStorageService;
import com.nastena.pawsitive.server.shelter.Shelter;
import com.nastena.pawsitive.utils.AnimalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final FileStorageService fileStorageService;

    private static final Pattern NAME_REGEX = Pattern.compile("^[A-Za-zА-Яа-я\\s]{2,50}$");


    public AnimalService(AnimalRepository animalRepository, FileStorageService fileStorageService) {
        this.animalRepository = animalRepository;
        this.fileStorageService = fileStorageService;
    }

    public Animal createAnimalOrThrow(
            Shelter shelter,
            CreateAnimalRequest createAnimalRequest,
            List<MultipartFile> animalPhotos,
            List<MultipartFile> passportPhotos) {

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

        if (animalPhotos != null) {
            List<String> photos = new ArrayList<>();
            for (MultipartFile file : animalPhotos) {
                String filename = fileStorageService.saveFile(file);
                photos.add(filename);
            }
            animal.setAnimalPhotos(photos);
        }

        if (passportPhotos != null) {
            List<String> photos = new ArrayList<>();
            for (MultipartFile file : passportPhotos) {
                String filename = fileStorageService.saveFile(file);
                photos.add(filename);
            }
            animal.setPassportPhotos(photos);
        }

        return animalRepository.save(animal);
    }

    public void updateAnimalOrThrow(UpdateAnimalRequest updateAnimalRequest,
                                    List<MultipartFile> newAnimalPhotos,
                                    List<MultipartFile> newPassportPhotos
    ) {
        Animal animal = animalRepository.findById(updateAnimalRequest.getId()).orElseThrow(() -> new ServerRuntimeException("Can not find animal by id", ErrorCode.INVALID_INPUT));

        String name = updateAnimalRequest.getName().trim();
        validateNameOrThrow(name);

        AnimalType type = updateAnimalRequest.getType();

        AnimalBreed breed = updateAnimalRequest.getBreed();
        validateBreedOrThrow(animal.getType(), breed);

        Long birthDate = updateAnimalRequest.getBirthDate();
        validateBirthDateOrThrow(birthDate);

        AnimalGender gender = updateAnimalRequest.getGender();
        String description = updateAnimalRequest.getDescription();

        animal.setName(name);
        animal.setType(type);
        animal.setBreed(breed);
        animal.setBirthDate(birthDate);
        animal.setGender(gender);
        animal.setDescription(description);

        ArrayList<String> animalPhotos = animal.getAnimalPhotos().stream()
                .filter(
                        (filename) -> !updateAnimalRequest.getRemovedAnimalPhotos().contains(filename)
                ).collect(Collectors.toCollection(ArrayList::new));


        if (newAnimalPhotos != null) {
            for (MultipartFile file : newAnimalPhotos) {
                String filename = fileStorageService.saveFile(file);
                animalPhotos.add(filename);
            }
        }
        animal.setAnimalPhotos(animalPhotos);

        ArrayList<String> passportPhotos = animal.getPassportPhotos().stream().filter(
                (filename) -> !updateAnimalRequest.getRemovedPassportPhotos().contains(filename)
        ).collect(Collectors.toCollection(ArrayList::new));

        if (newPassportPhotos != null) {
            for (MultipartFile file : newPassportPhotos) {
                String filename = fileStorageService.saveFile(file);
                passportPhotos.add(filename);
            }
        }
        animal.setPassportPhotos(passportPhotos);

        Stream.concat(
                        updateAnimalRequest.getRemovedAnimalPhotos().stream(),
                        updateAnimalRequest.getRemovedPassportPhotos().stream()
                )
                .forEach(fileStorageService::deleteFile);

        animalRepository.save(animal);
    }

    public List<Animal> getShelterAnimals(Shelter shelter) {
        return animalRepository.findAnimalsByShelter(shelter);
    }

    public Animal getShelterAnimalOrThrow(Shelter shelter, Long id) {
        Animal animal = animalRepository.findById(id).orElseThrow(
                () -> new ServerRuntimeException("Can't find animal by id", ErrorCode.INVALID_INPUT)
        );

        if (!animal.getShelter().getId().equals(shelter.getId())) {
            throw new ServerRuntimeException("Can't find animal of provided shelter", ErrorCode.INVALID_INPUT);
        }

        return animal;
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
