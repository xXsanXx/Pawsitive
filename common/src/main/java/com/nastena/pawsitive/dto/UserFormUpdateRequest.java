package com.nastena.pawsitive.dto;

public class UserFormUpdateRequest {
    private String name;
    private Long birthDate;
    private String profession;
    private String phone;


    public UserFormUpdateRequest() {
    }

    public UserFormUpdateRequest(String name, Long birthDate, String profession, String phone) {
        this.name = name;
        this.birthDate = birthDate;
        this.profession = profession;
        this.phone = phone;

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


