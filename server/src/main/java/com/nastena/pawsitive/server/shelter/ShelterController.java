package com.nastena.pawsitive.server.shelter;

import com.nastena.pawsitive.dto.AnimalResponse;
import com.nastena.pawsitive.dto.ShelterInfoResponse;
import com.nastena.pawsitive.dto.ShelterProfileResponse;
import com.nastena.pawsitive.dto.UpdateShelterProfileRequest;
import com.nastena.pawsitive.server.account.Account;
import com.nastena.pawsitive.server.account.AccountService;
import com.nastena.pawsitive.server.animal.Animal;
import com.nastena.pawsitive.server.animal.AnimalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/api/shelter")
public class ShelterController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ShelterService shelterService;

    @Autowired
    private AnimalService animalService;

    @GetMapping("/profile")
    public ResponseEntity<ShelterProfileResponse> getProfile(Authentication authentication) {
        String email = authentication.getName();

        log.info("[profile] Request profile for: {}", email);

        Account account = accountService.getAccountOrThrow(email);
        Shelter shelter = shelterService.getShelterOrThrow(account);

        log.info("[profile] Successfully: {}", shelter.getName());
        return ResponseEntity.ok(new ShelterProfileResponse(shelter.getName(), account.getEmail(), shelter.getPhone(),
                shelter.getAddress(), shelter.getInfo()));
    }

    @PostMapping("/profile/update")
    public ResponseEntity<?> updateShelterProfile(@RequestBody UpdateShelterProfileRequest updateShelterProfileRequest, Authentication authentication) {
        String email = authentication.getName();

        Account account = accountService.getAccountOrThrow(email);

        Shelter shelter = shelterService.getShelterOrThrow(account);


        shelterService.updateShelterPhoneOrThrow(shelter, updateShelterProfileRequest);


        return ResponseEntity.ok("Данные обновлены");
    }

    @PostMapping("/info")
    public ResponseEntity<?> getShelterInfo(@RequestBody Long id) {
        Shelter shelter = shelterService.getShelterOrThrow(id);

        List<Animal> animals = animalService.getShelterAnimals(shelter);

        return ResponseEntity.ok(
                new ShelterInfoResponse(
                        shelter.getId(), shelter.getName(), shelter.getAccount().getEmail(), shelter.getPhone(), shelter.getAddress(),
                        shelter.getInfo(), animals.stream().map(
                        animal -> new AnimalResponse(
                                animal.getId(),
                                animal.getShelter().getId(),
                                animal.getName(),
                                animal.getType(),
                                animal.getBreed(),
                                animal.getBirthDate(),
                                animal.getGender(),
                                animal.getDescription(),
                                animal.getAnimalPhotos(),
                                animal.getPassportPhotos()
                        )).toList()
                )
        );

    }


}
