package com.nastena.pawsitive.dto;

public class ShelterProfileResponse {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String info;

    public ShelterProfileResponse() {
    }


    public ShelterProfileResponse(String name, String email,
                                  String phone, String address,
                                  String info) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.info = info;
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
}
