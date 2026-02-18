package com.nastena.pawsitive.server.favorite;
import com.nastena.pawsitive.server.animal.Animal;
import com.nastena.pawsitive.server.animal.AnimalRepository;
import com.nastena.pawsitive.server.favorite.dto.FavoriteResponseDto;
import com.nastena.pawsitive.server.security.UserAuthService;
import com.nastena.pawsitive.server.user.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final AnimalRepository animalRepository;
    private final UserAuthService userAuthService;

    public FavoriteController(
            FavoriteService favoriteService,
            AnimalRepository animalRepository,
            UserAuthService userAuthService
    ) {
        this.favoriteService = favoriteService;
        this.animalRepository = animalRepository;
        this.userAuthService = userAuthService;
    }

    // ❤️ добавить в избранное
    @PostMapping("/{animalId}")
    public void addToFavorites(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long animalId
    ) {
        User user = userAuthService.getUserFromToken(authHeader);
        Animal animal = animalRepository.findById(animalId).orElseThrow();

        favoriteService.addToFavorites(user, animal);
    }

    // ❌ удалить из избранного
    @DeleteMapping("/{animalId}")
    public void removeFromFavorites(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long animalId
    ) {
        User user = userAuthService.getUserFromToken(authHeader);
        Animal animal = animalRepository.findById(animalId).orElseThrow();

        favoriteService.removeFromFavorites(user, animal);
    }

    // 📋 список избранного
    @GetMapping
    public List<FavoriteResponseDto> getFavorites(
            @RequestHeader("Authorization") String authHeader
    ) {
        User user = userAuthService.getUserFromToken(authHeader);

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
