package com.nastena.pawsitive.dto;

public class UpdateAnimalRequest {
    private Long id;
    private String name;
    private AnimalBreed breed;
    private Long birthDate;
    private AnimalGender gender;
    private String description;

    public UpdateAnimalRequest() {}


    public UpdateAnimalRequest(Long id, String name, AnimalBreed breed,
                               Long birthDate, AnimalGender gender, String description) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.birthDate = birthDate;
        this.gender = gender;
        this.description = description;
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
}