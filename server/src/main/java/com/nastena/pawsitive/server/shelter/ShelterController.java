package com.nastena.pawsitive.server.shelter;

import com.nastena.pawsitive.dto.ShelterProfileResponse;
import com.nastena.pawsitive.dto.UpdateShelterProfileRequest;
import com.nastena.pawsitive.server.account.Account;
import com.nastena.pawsitive.server.account.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/api/shelter")
public class ShelterController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ShelterService shelterService;

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
    public ResponseEntity<?> updateShelterProfile(@RequestBody UpdateShelterProfileRequest updateShelterProfileRequest, Authentication authentication)  {
        String email = authentication.getName();

        Account account = accountService.getAccountOrThrow(email);

        Shelter shelter = shelterService.getShelterOrThrow(account);


        shelterService.updateShelterPhoneOrThrow(shelter, updateShelterProfileRequest);


        return ResponseEntity.ok("Данные обновлены");
    }



}
