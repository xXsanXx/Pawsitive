package com.nastena.pawsitive.server.favorite;
import com.nastena.pawsitive.server.account.Account;
import com.nastena.pawsitive.server.account.AccountRepository;
import com.nastena.pawsitive.server.animal.Animal;
import com.nastena.pawsitive.server.animal.AnimalRepository;
import com.nastena.pawsitive.server.favorite.dto.FavoriteResponseDto;
import com.nastena.pawsitive.server.security.JwtUtils;
import com.nastena.pawsitive.server.user.User;
import com.nastena.pawsitive.server.user.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final JwtUtils jwtUtils;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;

    public FavoriteController(
            FavoriteService favoriteService, JwtUtils jwtUtils, AccountRepository accountRepository, UserRepository userRepository, AnimalRepository animalRepository
    ) {
        this.favoriteService = favoriteService;
        this.jwtUtils = jwtUtils;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.animalRepository = animalRepository;
    }

    private User getCurrentUser(String authHeader) {
        String token = authHeader.substring(7);
        String email = jwtUtils.getEmailFromToken(token);

        Account account = accountRepository.findByEmail(email).orElseThrow();
        return userRepository.findByAccount(account).orElseThrow();
    }

    @PostMapping("/{animalId}")
    public void addToFavorites(
            @RequestParam("Authorization") String autHeader,
            @PathVariable Long animalId
    ) {
        User user = getCurrentUser(autHeader);
        Animal animal = animalRepository.findById(animalId).orElseThrow();
        favoriteService.addToFavorites(user, animal);
    }

    @GetMapping
    public List<FavoriteResponseDto> getFavorites(
            @RequestHeader("Authorization") String authHeader
    ) {
        User user = getCurrentUser(authHeader);
        return favoriteService.getUserFavorites(user)
                .stream()
                .map(f -> new FavoriteResponseDto(
                        f.getAnimal().getId(),
                        f.getAnimal().getAnimalName(),
                        f.getAnimal().getType(),
                        f.getAnimal().getAge(),
                        f.getAnimal().getShelter().getShelterName()
                ))
                .toList();
    }
}