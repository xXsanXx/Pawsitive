package com.nastena.pawsitive.dto;

public class MeResponse {
    private AccountRole role;

    public MeResponse() {
    }

    public MeResponse(AccountRole role) {
        this.role = role;
    }

    public AccountRole getRole() {
        return role;
    }
}
