package com.nastena.pawsitive.server.account.dto;


import com.nastena.pawsitive.dto.AccountRole;

public record AccountRegisterRequest(String email, String password, AccountRole role) {
}
