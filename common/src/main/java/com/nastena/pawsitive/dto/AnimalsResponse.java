package com.nastena.pawsitive.dto;

import java.util.ArrayList;

public class AnimalsResponse {
    private ArrayList<AnimalResponse> animalResponses;

    public AnimalsResponse() {
    }

    public AnimalsResponse(ArrayList<AnimalResponse> animalResponses) {
        this.animalResponses = animalResponses;
    }

    public ArrayList<AnimalResponse> getAnimalResponses() {
        return animalResponses;
    }
}
