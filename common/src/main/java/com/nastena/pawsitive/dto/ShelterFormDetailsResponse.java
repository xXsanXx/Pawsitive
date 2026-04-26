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

    private String currentPets;
    private String previousPets;
    private String feedingExperience;
    private String vaccination;
    private String reason;
    private String petCareWhenAway;
    private String problemCharacter;
    private String healthIssues;
    private String additionalInfo;

    private String phone;

    public ShelterFormDetailsResponse() {}

    public ShelterFormDetailsResponse(Long requestId, String animalName, List<String> animalPhotos, AdoptionStatus status, String userName, Long birthDate, String profession, String currentPets, String previousPets, String feedingExperience, String vaccination, String reason, String petCareWhenAway, String problemCharacter, String healthIssues, String additionalInfo, String phone) {
        this.requestId = requestId;
        this.animalName = animalName;
        this.animalPhotos = animalPhotos;
        this.status = status;
        this.userName = userName;
        this.birthDate = birthDate;
        this.profession = profession;
        this.currentPets = currentPets;
        this.previousPets = previousPets;
        this.feedingExperience = feedingExperience;
        this.vaccination = vaccination;
        this.reason = reason;
        this.petCareWhenAway = petCareWhenAway;
        this.problemCharacter = problemCharacter;
        this.healthIssues = healthIssues;
        this.additionalInfo = additionalInfo;
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

    public String getCurrentPets() {
        return currentPets;
    }

    public String getPreviousPets() {
        return previousPets;
    }

    public String getFeedingExperience() {
        return feedingExperience;
    }

    public String getVaccination() {
        return vaccination;
    }

    public String getReason() {
        return reason;
    }

    public String getPetCareWhenAway() {
        return petCareWhenAway;
    }

    public String getProblemCharacter() {
        return problemCharacter;
    }

    public String getHealthIssues() {
        return healthIssues;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }
}
