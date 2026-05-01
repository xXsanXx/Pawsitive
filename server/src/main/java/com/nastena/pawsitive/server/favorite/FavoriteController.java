package com.nastena.pawsitive.server.favorite;

import com.nastena.pawsitive.dto.AdoptionStatus;
import com.nastena.pawsitive.dto.AnimalResponse;
import com.nastena.pawsitive.dto.AnimalStatus;
import com.nastena.pawsitive.dto.AnimalsResponse;
import com.nastena.pawsitive.server.account.Account;
import com.nastena.pawsitive.server.account.AccountService;
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

@Slf4j
@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private UserService userService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private AdoptionRequestService adoptionRequestService;

    @PostMapping("/add")
    public ResponseEntity<?> addToFavorite(@RequestBody Long id, Authentication authentication) {
        Account account = accountService.getAccountOrThrow(authentication.getName());

        User user = userService.getUserOrThrow(account);

        Animal animal = animalService.getAnimalOrThrow(id);

        favoriteService.addToFavorite(user, animal);

        return ResponseEntity.ok("Animal added to favorite");

    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeFromFavorite(@RequestBody Long id, Authentication authentication) {
        Account account = accountService.getAccountOrThrow(authentication.getName());
        User user = userService.getUserOrThrow(account);
        Animal animal = animalService.getAnimalOrThrow(id);

        favoriteService.removeFromFavorite(animal, user);

        return ResponseEntity.ok("Animal removed from favorite");
    }

    @GetMapping("/get")
    public ResponseEntity<AnimalsResponse> getUserFavorite(Authentication authentication) {
        Account account = accountService.getAccountOrThrow(authentication.getName());
        User user = userService.getUserOrThrow(account);

        List<Favorite> favorites = favoriteService.getUserFavorites(user);

        List<AnimalResponse> animals = favorites.stream()
                .map(Favorite::getAnimal)
                .filter(animal -> animal.getStatus() == AnimalStatus.IN_SHELTER)
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


}


