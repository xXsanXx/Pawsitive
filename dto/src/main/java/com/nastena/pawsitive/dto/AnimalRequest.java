package com.nastena.pawsitive.dto;

public class AnimalRequest {
    private String name;
    private String type;
    private String breed;
    private Integer age;
    private String gender;
    private String healthInfo;

    public AnimalRequest() {}


    public AnimalRequest(String name, String type, String breed,
                         Integer age, String gender, String healthInfo) {
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

    public String getGender() {
        return gender;
    }

    public String getHealthInfo() {
        return healthInfo;
    }

}