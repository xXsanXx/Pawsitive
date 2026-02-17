package com.nastena.pawsitive.server.favorite;

import com.nastena.pawsitive.server.animal.Animal;
import com.nastena.pawsitive.server.animal.AnimalRepository;
import com.nastena.pawsitive.server.favorite.dto.FavoriteResponseDto;
import com.nastena.pawsitive.server.user.User;
import com.nastena.pawsitive.server.user.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;

    public FavoriteController(
            FavoriteService favoriteService,
            UserRepository userRepository,
            AnimalRepository animalRepository
    ) {
        this.favoriteService = favoriteService;
        this.userRepository = userRepository;
        this.animalRepository = animalRepository;
    }

    @PostMapping("/{animalId}")
    public void addToFavorites(
            @RequestParam Long userId,
            @PathVariable Long animalId
    ) {
        User user = userRepository.findById(userId).orElseThrow();
        Animal animal = animalRepository.findById(animalId).orElseThrow();

        favoriteService.addToFavorites(user, animal);
    }

    @GetMapping
    public List<FavoriteResponseDto> getFavorites(@RequestParam Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

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