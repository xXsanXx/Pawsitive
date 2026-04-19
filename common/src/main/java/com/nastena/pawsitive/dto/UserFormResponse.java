package com.nastena.pawsitive.dto;

public class UserFormResponse {
    private String animalName;
    private String shelterName;
    private String name;
    private Long birthDate;
    private String profession;
    private String phone;

    public UserFormResponse() {}

    public UserFormResponse(String animalName, String shelterName, String name, Long birthDate, String profession, String phone) {
        this.animalName = animalName;
        this.shelterName = shelterName;
        this.name = name;
        this.birthDate = birthDate;
        this.profession = profession;
        this.phone = phone;
    }

    public String getAnimalName() {
        return animalName;
    }

    public String getShelterName() {
        return shelterName;
    }

    public String getName() {
        return name;
    }

    public Long getBirthDate() {
        return birthDate;
    }

    public String getProfession() {
        return profession;
    }

    public String getPhone() {
        return phone;
    }
}
