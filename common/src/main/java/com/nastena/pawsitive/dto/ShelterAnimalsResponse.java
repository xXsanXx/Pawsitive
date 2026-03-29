package com.nastena.pawsitive.dto;

import java.util.List;

public class ShelterAnimalsResponse {
    private List<ShelterAnimalResponse> animals;

    public ShelterAnimalsResponse() {
    }

    public ShelterAnimalsResponse(List<ShelterAnimalResponse> animals) {
        this.animals = animals;
    }

    public List<ShelterAnimalResponse> getAnimals() {
        return animals;
    }
}
