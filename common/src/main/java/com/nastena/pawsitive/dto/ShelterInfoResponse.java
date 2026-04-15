package com.nastena.pawsitive.dto;

import java.util.List;

public class ShelterInfoResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String info;
    private List<AnimalResponse> animals;

    public ShelterInfoResponse() {
    }


    public ShelterInfoResponse(Long id, String name, String email,
                               String phone, String address,
                               String info, List<AnimalResponse> animals) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.info = info;
        this.animals = animals;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() { return phone; }

    public String getAddress() { return address; }

    public String getInfo() { return info; }

    public Long getId() {
        return id;
    }

    public List<AnimalResponse> getAnimals() {
        return animals;
    }
}
