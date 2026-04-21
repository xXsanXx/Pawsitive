package com.nastena.pawsitive.dto;

import java.util.List;

public class AnimalResponse {
    private Long id;
    private Long shelterId;
    private String name;
    private AnimalType type;
    private AnimalBreed breed;
    private Long birthDate;
    private AnimalGender gender;
    private String description;

    private AdoptionStatus status;

    private List<String> animalPhotos;

    private List<String> passportPhotos;


    public AnimalResponse() {
    }


    public AnimalResponse(Long id, Long shelterId, String name, AnimalType type, AnimalBreed breed,
                          Long birthDate, AnimalGender gender, String description, AdoptionStatus status, List<String> photoUrls,
                          List<String> passportPhotoUrls) {
        this.id = id;
        this.shelterId = shelterId;
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.birthDate = birthDate;
        this.gender = gender;
        this.description = description;
        this.status = status;
        this.animalPhotos = photoUrls;
        this.passportPhotos = passportPhotoUrls;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AnimalType getType() {
        return type;
    }

    public AnimalBreed getBreed() {
        return breed;
    }

    public Long getBirthDate() {
        return birthDate;
    }

    public AnimalGender getGender() {
        return gender;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAnimalPhotos() {
        return animalPhotos;
    }

    public List<String> getPassportPhotos() {
        return passportPhotos;
    }

    public Long getShelterId() {
        return shelterId;
    }

    public AdoptionStatus getStatus() {
        return status;
    }
}