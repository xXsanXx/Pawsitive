package com.nastena.pawsitive.dto;

import java.util.List;

public class ShelterFormDetailsResponse {
    private Long requestId;

    private String animalName;
    private List<String> animalPhotos;
    private AdoptionStatus status;

    private String userName;
    private Long birthDate;
    private String profession;
    private String phone;

    public ShelterFormDetailsResponse() {}

    public ShelterFormDetailsResponse(Long requestId, String animalName, List<String> animalPhotos, AdoptionStatus status, String userName, Long birthDate, String profession, String phone) {
        this.requestId = requestId;
        this.animalName = animalName;
        this.animalPhotos = animalPhotos;
        this.status = status;
        this.userName = userName;
        this.birthDate = birthDate;
        this.profession = profession;
        this.phone = phone;
    }

    public Long getRequestId() {
        return requestId;
    }

    public String getAnimalName() {
        return animalName;
    }

    public List<String> getAnimalPhotos() {
        return animalPhotos;
    }

    public String getUserName() {
        return userName;
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

    public AdoptionStatus getStatus() {
        return status;
    }
}
