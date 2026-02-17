package com.nastena.pawsitive.server.account.dto;

public class AccountResponseDto {
    private String token;
    private String role;

    public AccountResponseDto(String token, String role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }
}
