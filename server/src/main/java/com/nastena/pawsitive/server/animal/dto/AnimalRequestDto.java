package com.nastena.pawsitive.server.animal.dto;

public class AnimalRequestDto {
    private String name;
    private String type;
    private String breed;
    private Integer age;
    private String gender;
    private String healthInfo;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
