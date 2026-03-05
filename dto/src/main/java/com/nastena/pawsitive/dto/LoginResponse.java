package com.nastena.pawsitive.dto;

public class LoginResponse {
    private String token;
    private AccountRole role;

    public LoginResponse() {
    }

    public LoginResponse(String token, AccountRole role) {

        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public AccountRole getRole() {
        return role;
    }
}
