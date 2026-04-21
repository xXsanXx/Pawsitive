package com.nastena.pawsitive.dto;

public class UserAdoptionResponse {
    private Long id;
    private String animalName;
    private String shelterName;
    private AdoptionStatus status;

    public UserAdoptionResponse() {}


    public UserAdoptionResponse(Long id, String animalName, String shelterName, AdoptionStatus status) {
        this.id = id;
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

    public Long getId() {
        return id;
    }
}
