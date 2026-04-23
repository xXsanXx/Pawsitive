package com.nastena.pawsitive.dto;

import java.util.List;


public class ShelterFormsResponse {
    private List<ShelterFormResponse> shelterFormsResponse;

    public ShelterFormsResponse() {
    }

    public ShelterFormsResponse(List<ShelterFormResponse> shelterFormsResponse) {
        this.shelterFormsResponse = shelterFormsResponse;
    }


    public List<ShelterFormResponse> getShelterFormsResponse() {
        return shelterFormsResponse;
    }
}

