package com.nastena.pawsitive.server.animal;

import com.nastena.pawsitive.dto.*;
import com.nastena.pawsitive.server.account.Account;
import com.nastena.pawsitive.server.account.AccountService;
import com.nastena.pawsitive.server.shelter.Shelter;
import com.nastena.pawsitive.server.shelter.ShelterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;


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
    public ResponseEntity<Long> createAnimal(

            @RequestPart("data") CreateAnimalRequest data,
            @RequestParam(value = "photos", required = false) List<MultipartFile> photos,
            @RequestParam(value = "vetPassports", required = false) List<MultipartFile> vetPassports,
            Authentication authentication

    ) throws Exception {

        String email = authentication.getName();

        Account account = accountService.getAccountOrThrow(email);
        Shelter shelter = shelterService.getShelterOrThrow(account);

        Animal animal = animalService.createAnimalOrThrow(
                shelter,
                data,
                photos,
                vetPassports
        );

        return ResponseEntity.ok(animal.getId());
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateAnimal(@RequestBody UpdateAnimalRequest updateAnimalRequest) {

        log.info("[update] name {}, breed {}", updateAnimalRequest.getName(), updateAnimalRequest.getBreed());

        animalService.updateAnimalOrThrow(updateAnimalRequest);


        return ResponseEntity.ok("Animal data updated");
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeAnimal(@RequestBody Long id) {

        log.info("[remove] id {}", id);

        animalService.removeAnimalOrThrow(id);

        return ResponseEntity.ok("Animal removed");
    }


    @GetMapping("/shelters")
    public ResponseEntity<ShelterAnimalsResponse> getShelterAnimals(Authentication authentication) {

        Account account = accountService.getAccountOrThrow(authentication.getName());

        log.info("[shelter animals] email {}", authentication.getName());

        Shelter shelter = shelterService.getShelterOrThrow(account);

        List<Animal> animals = animalService.getShelterAnimals(shelter);

        List<ShelterAnimalResponse> animalResponses = animals.stream()
                .map(animal -> new ShelterAnimalResponse(
                                animal.getId(), animal.getName(), animal.getType(),
                                animal.getBreed(), animal.getBirthDate(), animal.getGender(), animal.getDescription()
                        )
                ).toList();

        log.info("[shelter animals] sending {} animals :: year {}",
                animalResponses.size(), LocalDate.now().getYear()
        );

        return ResponseEntity.ok(new ShelterAnimalsResponse(animalResponses));
    }



}
