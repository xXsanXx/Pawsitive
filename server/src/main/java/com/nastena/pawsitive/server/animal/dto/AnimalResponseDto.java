package com.nastena.pawsitive.server.animal.dto;

import com.nastena.pawsitive.server.animal.Animal;

public class AnimalResponseDto {
    private Long id;
    private String name;
    private String type;
    private String breed;
    private Integer age;
    private String gender;
    private String healthInfo;
    private String shelterName;


    public AnimalResponseDto(Animal animal) {
        this.id = animal.getId();
        this.name = animal.getName();
        this.type = animal.getType();
        this.breed = animal.getBreed();
        this.age = animal.getAge();
        this.gender = animal.getGender().name();
        this.healthInfo = animal.getHealthInfo();
        this.shelterName = animal.getShelter().getName();
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

    public String getGender() {
        return gender;
    }

    public String getHealthInfo() {
        return healthInfo;
    }

    public String getShelterName() {
        return shelterName;
    }
}
