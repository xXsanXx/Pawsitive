package com.nastena.pawsitive.server.account.dto;

public record AccountRegisterRequest(String email, String password, AccountRole role) {
}
