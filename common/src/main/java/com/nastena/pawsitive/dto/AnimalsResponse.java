package com.nastena.pawsitive.dto;

import java.util.List;

public class AnimalsResponse {
    private List<AnimalResponse> animals;

    public AnimalsResponse() {
    }

    public AnimalsResponse(List<AnimalResponse> animals) {
        this.animals = animals;
    }

    public List<AnimalResponse> getAnimals() {
        return animals;
    }
}
