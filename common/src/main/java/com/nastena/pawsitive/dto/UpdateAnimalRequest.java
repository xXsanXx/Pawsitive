package com.nastena.pawsitive.dto;

import java.util.List;

public class UpdateAnimalRequest {
    private Long id;
    private String name;
    private AnimalType type;
    private AnimalBreed breed;
    private Long birthDate;
    private AnimalGender gender;
    private String description;

    private List<String> removedAnimalPhotos;

    private List<String> removedPassportPhotos;

    public UpdateAnimalRequest() {}


    public UpdateAnimalRequest(Long id, String name, AnimalType type, AnimalBreed breed,
                               Long birthDate, AnimalGender gender, String description, List<String> removedAnimalPhotos, List<String> removedPassportPhotos) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.birthDate = birthDate;
        this.gender = gender;
        this.description = description;
        this.removedAnimalPhotos = removedAnimalPhotos;
        this.removedPassportPhotos = removedPassportPhotos;
    }



    public String getName() {
        return name;
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

    public Long getId() {
        return id;
    }

    public AnimalType getType() {
        return type;
    }

    public List<String> getRemovedAnimalPhotos() {
        return removedAnimalPhotos;
    }

    public void setRemovedAnimalPhotos(List<String> removedAnimalPhotos) {
        this.removedAnimalPhotos = removedAnimalPhotos;
    }

    public List<String> getRemovedPassportPhotos() {
        return removedPassportPhotos;
    }

    public void setRemovedPassportPhotos(List<String> removedPassportPhotos) {
        this.removedPassportPhotos = removedPassportPhotos;
    }
}