package com.nastena.pawsitive.dto;

import java.text.Normalizer;

public class FormRequest {
    private String fullName;
    private String age;
    private String profession;
    private String phone;


    public FormRequest() {
    }

    public FormRequest(String fullName, String age, String profession, String phone) {
        this.fullName = fullName;
        this.age = age;
        this.profession = profession;
        this.phone = phone;

    }

    public String getFullName() {
        return fullName;
    }

    public String getAge() {
        return age;
    }

    public String getProfession() {
        return profession;
    }

    public String getPhone() {
        return phone;
    }
}


