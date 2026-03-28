package com.nastena.pawsitive.server.animal;

import com.nastena.pawsitive.dto.CreateAnimalRequest;
import com.nastena.pawsitive.server.account.Account;
import com.nastena.pawsitive.server.account.AccountService;
import com.nastena.pawsitive.server.shelter.Shelter;
import com.nastena.pawsitive.server.shelter.ShelterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("api/animals")
public class AnimalController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ShelterService shelterService;

    @Autowired
    private AnimalService animalService;

    @PostMapping("/create")
    public ResponseEntity<Long> createAnimal(@RequestBody CreateAnimalRequest createAnimalRequest, Authentication authentication) {
        String email = authentication.getName();

        Account account = accountService.getAccountOrThrow(email);

        Shelter shelter = shelterService.getShelterOrThrow(account);

        Animal animal = animalService.createAnimalOrThrow(shelter, createAnimalRequest);

        return ResponseEntity.ok(animal.getId());
    }


}
