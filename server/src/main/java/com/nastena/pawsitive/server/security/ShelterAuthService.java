package com.nastena.pawsitive.server.security;

import com.nastena.pawsitive.server.account.Account;
import com.nastena.pawsitive.server.account.AccountRepository;
import com.nastena.pawsitive.server.shelter.Shelter;
import com.nastena.pawsitive.server.shelter.ShelterRepository;
import org.springframework.stereotype.Service;

@Service
public class ShelterAuthService {

    private final JwtUtils jwtUtils;
    private final AccountRepository accountRepository;
    private final ShelterRepository shelterRepository;

    public ShelterAuthService(
            JwtUtils jwtUtils,
            AccountRepository accountRepository,
            ShelterRepository shelterRepository
            ) {
        this.jwtUtils = jwtUtils;
        this.accountRepository = accountRepository;
        this.shelterRepository = shelterRepository;
    }

    public Shelter getShelterFromToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtils.getEmailFromTokenOrThrow(token);

        Account account = accountRepository.findByEmail(email)
                .orElseThrow();

        return shelterRepository.findByAccount(account)
                .orElseThrow();
    }
}
