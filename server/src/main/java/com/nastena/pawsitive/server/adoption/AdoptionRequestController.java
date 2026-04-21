package com.nastena.pawsitive.server.adoption;

import com.nastena.pawsitive.dto.UserAdoptionResponse;
import com.nastena.pawsitive.dto.UserAdoptionsResponse;
import com.nastena.pawsitive.server.account.Account;
import com.nastena.pawsitive.server.account.AccountService;
import com.nastena.pawsitive.server.user.User;
import com.nastena.pawsitive.server.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("api/adoption")
public class AdoptionRequestController {

    @Autowired
    private AdoptionRequestService adoptionRequestService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @PostMapping("/form/create")
    public ResponseEntity<?> sendForm(@RequestBody Long animalId, Authentication authentication) {

        log.info("[adoption] Request to create form");
        log.info("[adoption] AnimalId: {}", animalId);
        log.info("[adoption] User email: {}", authentication.getName());

        Account account = accountService.getAccountOrThrow(authentication.getName());
        User user = userService.getUserOrThrow(account);

        log.info("[adoption] User found: {}", user.getName());


        adoptionRequestService.createFormOrThrow(user, animalId);

        log.info("[adoption] Adoption form successfully created");

        return ResponseEntity.ok("Form sent");
    }

    @GetMapping("/requests")
    public ResponseEntity<UserAdoptionsResponse> getRequests(Authentication authentication) {
        log.info("[user requests] email {}", authentication.getName());

        Account account = accountService.getAccountOrThrow(authentication.getName());
        User user = userService.getUserOrThrow(account);

        List<AdoptionRequest> requests = adoptionRequestService.getRequestsByUser(user);

        List<UserAdoptionResponse> responses = requests.stream()
                .map(request -> new UserAdoptionResponse(
                        request.getAnimal().getName(),
                        request.getAnimal().getShelter().getName(),
                        request.getStatus()
                ))
                .toList();

        log.info("[user requests] sending {} requests", responses.size());

        return ResponseEntity.ok(new UserAdoptionsResponse(responses));
    }
}
