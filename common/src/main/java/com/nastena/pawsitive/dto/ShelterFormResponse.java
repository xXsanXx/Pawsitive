package com.nastena.pawsitive.dto;

import java.util.List;

public class ShelterFormResponse {

    private Long requestId;

    private Long animalId;
    private String animalName;
    private List<String> animalPhotos;
    private AdoptionStatus status;

    private Long userId;
    private String userName;

    public ShelterFormResponse() {}

    public ShelterFormResponse(Long requestId, Long animalId, String animalName, List<String> animalPhotos, AdoptionStatus status, Long userId, String userName) {
        this.requestId = requestId;
        this.animalId = animalId;
        this.animalName = animalName;
        this.animalPhotos = animalPhotos;
        this.status = status;
        this.userId = userId;
        this.userName = userName;
    }

    public Long getRequestId() {
        return requestId;
    }

    public Long getAnimalId() {
        return animalId;
    }

    public String getAnimalName() {
        return animalName;
    }

    public List<String> getAnimalPhotos() {
        return animalPhotos;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public AdoptionStatus getStatus() {
        return status;
    }
}
