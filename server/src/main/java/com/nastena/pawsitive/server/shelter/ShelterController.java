package com.nastena.pawsitive.server.shelter;

import com.nastena.pawsitive.dto.*;
import com.nastena.pawsitive.server.account.Account;
import com.nastena.pawsitive.server.account.AccountService;
import com.nastena.pawsitive.server.adoption.AdoptionRequest;
import com.nastena.pawsitive.server.adoption.AdoptionRequestService;
import com.nastena.pawsitive.server.animal.Animal;
import com.nastena.pawsitive.server.animal.AnimalService;
import com.nastena.pawsitive.server.user.User;
import com.nastena.pawsitive.server.user.UserService;
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

    @Autowired
    private UserService userService;



    @Autowired
    private AdoptionRequestService adoptionRequestService;

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
    public ResponseEntity<?> getShelterInfo(@RequestBody Long id, Authentication authentication) {
        Account account = accountService.getAccountOrThrow(authentication.getName());
        User user = userService.getUserOrThrow(account);

        Shelter shelter = shelterService.getShelterOrThrow(id);

        List<Animal> animals = animalService.getShelterAnimals(shelter);


        return ResponseEntity.ok(
                new ShelterInfoResponse(
                        shelter.getId(), shelter.getName(), shelter.getAccount().getEmail(), shelter.getPhone(), shelter.getAddress(),
                        shelter.getInfo(), animals.stream().map(
                        animal -> {
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
                                    animal.getPassportPhotos());
                        }).toList()
                )
        );

    }

    @GetMapping("/forms")
    public ResponseEntity<ShelterFormsResponse> getRequests(Authentication authentication) {
        log.info("[shelter requests] email {}", authentication.getName());

        Account account = accountService.getAccountOrThrow(authentication.getName());
        Shelter shelter = shelterService.getShelterOrThrow(account);


        List<AdoptionRequest> requests = adoptionRequestService.getVisibleRequestsByShelter(shelter);

        List<ShelterFormResponse> responses = requests.stream()
                .map(request -> new ShelterFormResponse(
                        request.getId(),
                        request.getAnimal().getId(),
                        request.getAnimal().getName(),
                        request.getAnimal().getAnimalPhotos(),
                        request.getStatus(),
                        request.getUser().getId(),
                        request.getUser().getName()
                ))
                .toList();

        log.info("[shelter requests] sending {} requests", responses.size());

        return ResponseEntity.ok(new ShelterFormsResponse(responses));
    }

    @PostMapping("/forms/details")
    public ResponseEntity<?> getShelterFormDetails(@RequestBody Long requestId) {

        AdoptionRequest request = adoptionRequestService.getRequestOrThrow(requestId);

        User user = request.getUser();

        ShelterFormDetailsResponse response = new ShelterFormDetailsResponse(
                request.getId(),
                request.getAnimal().getName(),
                request.getAnimal().getAnimalPhotos(),
                request.getStatus(),
                user.getName(),
                user.getBirthDate(),
                user.getProfession(),
                user.getCurrentPets(),
                user.getPreviousPets(),
                user.getFeedingExperience(),
                user.getVaccination(),
                user.getReason(),
                user.getPetCareWhenAway(),
                user.getProblemCharacter(),
                user.getHealthIssues(),
                user.getAdditionalInfo(),
                user.getPhone()
        );

        return ResponseEntity.ok(response);
    }
    @PostMapping("/forms/update")
    public ResponseEntity<?> updateStatus(@RequestBody ShelterUpdateStatusRequest shelterUpdateStatusRequest) {
        adoptionRequestService.updateStatusOrThrow(
                shelterUpdateStatusRequest.getRequestId(),
                shelterUpdateStatusRequest.getStatus()
        );
        return ResponseEntity.ok().build();
    }

}
