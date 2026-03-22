package com.nastena.pawsitive.dto;

public class UpdateShelterProfileRequest {
    private String phone;
    private String address;
    private String info;

    public UpdateShelterProfileRequest() {}

    public UpdateShelterProfileRequest(String phone, String address, String info) {
        this.phone = phone;
        this.address = address;
        this.info = info;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getInfo() {
        return info;
    }
}
