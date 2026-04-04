package com.nastena.pawsitive.server.animal;

import com.nastena.pawsitive.dto.CreateAnimalRequest;
import com.nastena.pawsitive.dto.ShelterAnimalResponse;
import com.nastena.pawsitive.dto.ShelterAnimalsResponse;
import com.nastena.pawsitive.dto.UpdateAnimalRequest;
import com.nastena.pawsitive.server.account.Account;
import com.nastena.pawsitive.server.account.AccountService;
import com.nastena.pawsitive.server.shelter.Shelter;
import com.nastena.pawsitive.server.shelter.ShelterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<?> updateAnimal(
            @RequestPart("data") UpdateAnimalRequest data,
            @RequestParam(value = "newPhotos", required = false) List<MultipartFile> newPhotos,
            @RequestParam(value = "newPassportPhotos", required = false) List<MultipartFile> newPassportPhotos) {

        log.info("[update] name {}, breed {}", data.getName(), data.getBreed());

        animalService.updateAnimalOrThrow(
                data,
                newPhotos,
                newPassportPhotos
        );

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
                                animal.getBreed(), animal.getBirthDate(), animal.getGender(),
                                animal.getDescription(), animal.getPhotoUrls(), animal.getVetPassportUrls()
                        )
                ).toList();

        log.info("[shelter animals] sending {} animals",
                animalResponses.size()
        );

        return ResponseEntity.ok(new ShelterAnimalsResponse(animalResponses));
    }


    @PostMapping("/shelters/id")
    public ResponseEntity<ShelterAnimalResponse> getSheltersAnimal(@RequestBody Long id, Authentication authentication) {
        Account account = accountService.getAccountOrThrow(authentication.getName());

        log.info("[shelter animals] email {}", authentication.getName());

        Shelter shelter = shelterService.getShelterOrThrow(account);

        Animal animal = animalService.getShelterAnimalOrThrow(shelter, id);

        ShelterAnimalResponse shelterAnimalResponse = new ShelterAnimalResponse(
                animal.getId(), animal.getName(), animal.getType(),
                animal.getBreed(), animal.getBirthDate(), animal.getGender(),
                animal.getDescription(), animal.getPhotoUrls(), animal.getVetPassportUrls()
        );


        return ResponseEntity.ok(shelterAnimalResponse);
    }


}
