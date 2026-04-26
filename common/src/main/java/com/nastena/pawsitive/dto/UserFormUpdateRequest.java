package com.nastena.pawsitive.dto;

public class UserFormUpdateRequest {
    private String name;
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


    public UserFormUpdateRequest() {
    }

    public UserFormUpdateRequest(String name, Long birthDate, String profession, String currentPets, String previousPets, String feedingExperience, String vaccination, String reason, String petCareWhenAway, String problemCharacter, String healthIssues, String additionalInfo, String phone) {
        this.name = name;
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

    public String getName() {
        return name;
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


