package com.nastena.pawsitive.dto;

import java.util.List;

public class ShelterAnimalResponse {
    private Long id;
    private String name;
    private AnimalType type;
    private AnimalBreed breed;
    private Long birthDate;
    private AnimalGender gender;
    private String description;

    private List<String> photoUrls;


    public ShelterAnimalResponse() {
    }


    public ShelterAnimalResponse(Long id, String name, AnimalType type, AnimalBreed breed,
                                 Long birthDate, AnimalGender gender, String description, List<String> photoUrls) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.birthDate = birthDate;
        this.gender = gender;
        this.description = description;
        this.photoUrls = photoUrls;
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

    public List<String> getPhotoUrls() {
        return photoUrls;
    }
}