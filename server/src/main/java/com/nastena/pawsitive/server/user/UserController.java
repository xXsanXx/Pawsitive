package com.nastena.pawsitive.server.user;

import com.nastena.pawsitive.dto.AnimalResponse;
import com.nastena.pawsitive.dto.UserFormResponse;
import com.nastena.pawsitive.dto.UserFormUpdateRequest;
import com.nastena.pawsitive.dto.UserProfileResponse;
import com.nastena.pawsitive.server.account.Account;
import com.nastena.pawsitive.server.account.AccountService;
import com.nastena.pawsitive.server.animal.Animal;
import com.nastena.pawsitive.server.animal.AnimalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private AnimalService animalService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(Authentication authentication) {
        String email = authentication.getName();

        log.info("[profile] Request profile for: {}", email);

        Account account = accountService.getAccountOrThrow(email);
        User user = userService.getUserOrThrow(account);

        log.info("[profile] Successfully: {}", user.getName());
        return ResponseEntity.ok(new UserProfileResponse(user.getName(), account.getEmail()));
    }

    @PostMapping("/form/update")
    public ResponseEntity<?> updateFormRequest(@RequestBody UserFormUpdateRequest request, Authentication authentication) {
        log.info("[user form] Email: {}", authentication.getName());

        Account account = accountService.getAccountOrThrow(authentication.getName());
        User user = userService.getUserOrThrow(account);

        userService.updateFormOrThrow(user,request);

        return ResponseEntity.ok("Form updated successfully");
    }

    @PostMapping("/form/get")
    public ResponseEntity<UserFormResponse> getForm(@RequestBody Long id, Authentication authentication) {
        Account account = accountService.getAccountOrThrow(authentication.getName());
        User user = userService.getUserOrThrow(account);

        Animal animal = animalService.getAnimalOrThrow(id);

        UserFormResponse userFormResponse = new UserFormResponse(
                animal.getName(),
                animal.getShelter().getName(),
                user.getName(),
                user.getBirthDate(),
                user.getProfession(),
                user.getPhone()
        );

        return ResponseEntity.ok(userFormResponse);
    }




}
