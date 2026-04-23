package com.nastena.pawsitive.dto;

import java.util.List;

public class UserAdoptionResponse {
    private Long id;
    private Long animalId;
    private String animalName;
    private String shelterName;
    private AdoptionStatus status;

    private List<String> animalPhotos;

    public UserAdoptionResponse() {
    }


    public UserAdoptionResponse(Long id, Long animalId, String animalName, String shelterName, AdoptionStatus status, List<String> animalPhotos) {
        this.id = id;
        this.animalId = animalId;
        this.animalName = animalName;
        this.shelterName = shelterName;
        this.status = status;
        this.animalPhotos = animalPhotos;
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

    public List<String> getAnimalPhotos() {
        return animalPhotos;
    }
}
