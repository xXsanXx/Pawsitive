package com.nastena.pawsitive.server.animal;

import com.nastena.pawsitive.dto.*;
import com.nastena.pawsitive.server.account.Account;
import com.nastena.pawsitive.server.account.AccountService;
import com.nastena.pawsitive.server.adoption.AdoptionRequestService;
import com.nastena.pawsitive.server.shelter.Shelter;
import com.nastena.pawsitive.server.shelter.ShelterService;
import com.nastena.pawsitive.server.user.User;
import com.nastena.pawsitive.server.user.UserAnimalsQueueService;
import com.nastena.pawsitive.server.user.UserService;
import com.nastena.pawsitive.utils.AnimalUtils;
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

    @Autowired
    private UserService userService;

    @Autowired
    private UserAnimalsQueueService userAnimalsQueueService;

    @Autowired
    private AdoptionRequestService adoptionRequestService;


    @PostMapping("/create")
    public ResponseEntity<Long> createAnimal(
            @RequestPart("data") CreateAnimalRequest data,
            @RequestParam(value = AnimalUtils.RequestParams.ANIMAL_PHOTOS, required = false) List<MultipartFile> photos,
            @RequestParam(value = AnimalUtils.RequestParams.PASSPORT_PHOTOS, required = false) List<MultipartFile> vetPassports,
            Authentication authentication

    ) {

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
            @RequestParam(value = AnimalUtils.RequestParams.ANIMAL_PHOTOS, required = false) List<MultipartFile> newPhotos,
            @RequestParam(value = AnimalUtils.RequestParams.PASSPORT_PHOTOS, required = false) List<MultipartFile> newPassportPhotos) {

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
                                animal.getDescription(), animal.getAnimalPhotos(), animal.getPassportPhotos()
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
                animal.getDescription(), animal.getAnimalPhotos(), animal.getPassportPhotos()
        );


        return ResponseEntity.ok(shelterAnimalResponse);
    }

    @PostMapping("/user/random")
    public ResponseEntity<AnimalsResponse> getRandomUserAnimalsRation(Authentication authentication) {
        Account account = accountService.getAccountOrThrow(authentication.getName());
        User user = userService.getUserOrThrow(account);

        List<AnimalResponse> animals = userAnimalsQueueService.getNextRation(user).stream()
                .map(animal -> {
                    AdoptionStatus status = adoptionRequestService.getStatus(user, animal);
                    return new AnimalResponse(
                            animal.getId(),
                            animal.getShelter().getId(),
                            animal.getName(),
                            animal.getType(),
                            animal.getBreed(),
                            animal.getBirthDate(),
                            animal.getGender(),
                            animal.getDescription(),
                            status,
                            animal.getAnimalPhotos(),
                            animal.getPassportPhotos()
                    );
                })
                .toList();
        return ResponseEntity.ok(new AnimalsResponse(animals));
    }

    @PostMapping("/users/id")
    public ResponseEntity<AnimalResponse> getAnimalDetails(@RequestBody Long id, Authentication authentication) {
        Account account = accountService.getAccountOrThrow(authentication.getName());
        User user = userService.getUserOrThrow(account);

        Animal animal = animalService.getAnimalOrThrow(id);

        AdoptionStatus status = adoptionRequestService.getStatus(user, animal);
        AnimalResponse animalResponse = new AnimalResponse(
                animal.getId(),
                animal.getShelter().getId(),
                animal.getName(),
                animal.getType(),
                animal.getBreed(),
                animal.getBirthDate(),
                animal.getGender(),
                animal.getDescription(),
                status,
                animal.getAnimalPhotos(),
                animal.getPassportPhotos()
        );

        return ResponseEntity.ok(animalResponse);
    }

}
