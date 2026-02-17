package com.nastena.pawsitive.server.favorite.dto;

public class FavoriteResponseDto {

    private Long animalId;
    private String animalName;
    private String type;
    private Integer age;
    private String shelterName;

    public FavoriteResponseDto(
            Long animalId,
            String animalName,
            String type,
            Integer age,
            String shelterName
    ) {
        this.animalId = animalId;
        this.animalName = animalName;
        this.type = type;
        this.age = age;
        this.shelterName = shelterName;
    }

    public Long getAnimalId() {
        return animalId;
    }

    public String getAnimalName() {
        return animalName;
    }

    public String getType() {
        return type;
    }

    public Integer getAge() {
        return age;
    }

    public String getShelterName() {
        return shelterName;
    }
}