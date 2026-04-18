package com.nastena.pawsitive.server.adoption;

import com.nastena.pawsitive.server.account.Account;
import com.nastena.pawsitive.server.account.AccountService;
import com.nastena.pawsitive.server.user.User;
import com.nastena.pawsitive.server.user.UserService;
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

        Account account = accountService.getAccountOrThrow(authentication.getName());
        User user = userService.getUserOrThrow(account);

        adoptionRequestService.createFormOrThrow(user, animalId);

        return ResponseEntity.ok("Form sent");
    }
}
