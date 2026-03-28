package com.nastena.pawsitive.dto;

public class CreateAnimalRequest {
    private String name;
    private String type;
    private String breed;
    private Integer age;
    private AnimalGender gender;
    private String healthInfo;

    public CreateAnimalRequest() {}


    public CreateAnimalRequest(String name, String type, String breed,
                               Integer age, AnimalGender gender, String healthInfo) {
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.healthInfo = healthInfo;
    }



    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getBreed() {
        return breed;
    }

    public Integer getAge() {
        return age;
    }

    public AnimalGender getGender() {
        return gender;
    }

    public String getHealthInfo() {
        return healthInfo;
    }

}