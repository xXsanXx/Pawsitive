package com.nastena.pawsitive.dto;

public class CreateAnimalRequest {
    private String name;
    private AnimalType type;
    private AnimalBreed breed;
    private Long birthDate;
    private AnimalGender gender;
    private String description;

    public CreateAnimalRequest() {}


    public CreateAnimalRequest(String name, AnimalType type, AnimalBreed breed,
                               Long birthDate, AnimalGender gender, String description) {
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.birthDate = birthDate;
        this.gender = gender;
        this.description = description;
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

}