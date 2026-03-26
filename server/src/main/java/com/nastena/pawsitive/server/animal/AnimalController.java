package com.nastena.pawsitive.server.animal;

import com.nastena.pawsitive.dto.AnimalResponse;
import com.nastena.pawsitive.dto.ShelterProfileResponse;
import com.nastena.pawsitive.server.account.Account;
import com.nastena.pawsitive.server.shelter.Shelter;
import com.nastena.pawsitive.server.shelter.ShelterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("api/animals")
public class AnimalController {

    @Autowired
    private ShelterService shelterService;

    @Autowired
    private AnimalService animalService;

    @GetMapping("/animal")
    public ResponseEntity<AnimalResponse> getAnimal(Authentication authentication) {

    }



}
