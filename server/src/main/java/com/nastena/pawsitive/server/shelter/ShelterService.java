package com.nastena.pawsitive.server.shelter;

import com.nastena.pawsitive.server.account.Account;
import org.springframework.stereotype.Service;

@Service
public class ShelterService {
    private final ShelterRepository shelterRepository;

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    public Shelter createShelter(Account account) {
        Shelter shelter = new Shelter(account);
        return shelterRepository.save(shelter);
    }
}
