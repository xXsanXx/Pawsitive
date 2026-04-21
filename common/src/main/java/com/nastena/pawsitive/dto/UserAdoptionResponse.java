package com.nastena.pawsitive.dto;

public class UserAdoptionResponse {
    private Long id;
    private Long animalId;
    private String animalName;
    private String shelterName;
    private AdoptionStatus status;

    public UserAdoptionResponse() {
    }


    public UserAdoptionResponse(Long id, Long animalId, String animalName, Long shelterId, String shelterName, AdoptionStatus status) {
        this.id = id;
        this.animalId = animalId;
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

    public Long getAnimalId() {
        return animalId;
    }
}
