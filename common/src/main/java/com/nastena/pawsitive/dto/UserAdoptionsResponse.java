package com.nastena.pawsitive.dto;

import java.util.List;

public class UserAdoptionsResponse {
    private List<UserAdoptionResponse> adoptionsResponse;

    public UserAdoptionsResponse() {
    }

    public UserAdoptionsResponse(List<UserAdoptionResponse> adoptionsResponse) {
        this.adoptionsResponse = adoptionsResponse;
    }


    public List<UserAdoptionResponse> getAdoptionsResponse() {
        return adoptionsResponse;
    }
}