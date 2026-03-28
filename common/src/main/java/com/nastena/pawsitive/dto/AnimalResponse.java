package com.nastena.pawsitive.dto;

public class AnimalResponse {
    private Long id;
    private String name;
    private String type;
    private String breed;
    private Integer age;
    private AnimalGender gender;
    private String healthInfo;
    private String shelterName;

    public AnimalResponse() {}


    public AnimalResponse(Long id, String name, String type, String breed,
                          Integer age, AnimalGender gender, String healthInfo, String shelterName) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.healthInfo = healthInfo;
        this.shelterName = shelterName;
    }


    public Long getId() {
        return id;
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

    public String getShelterName() {
        return shelterName;
    }
}