package com.nastena.pawsitive.dto;

public class UserAdoptionResponse {
    private String animalName;
    private String shelterName;
    private AdoptionStatus status;

    public UserAdoptionResponse() {}


    public UserAdoptionResponse(String animalName, String shelterName, AdoptionStatus status) {
        this.animalName = animalName;
        this.shelterName = shelterName;
        this.status = status;
    }

    public String getAnimalName() {
        return animalName;
    }

    public String getShelterName() {
        return shelterName;
    }

    public AdoptionStatus getStatus() {
        return status;
    }
}
