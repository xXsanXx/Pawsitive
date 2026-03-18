package com.nastena.pawsitive.dto;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private AccountRole role;

    public RegisterRequest() {
    }

    public RegisterRequest(String name, String email, String password, AccountRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }


    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }

    public AccountRole getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }
}
