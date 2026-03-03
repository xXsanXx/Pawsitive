package com.nastena.pawsitive.server.favorite;

import com.nastena.pawsitive.server.animal.Animal;
import com.nastena.pawsitive.server.animal.AnimalRepository;
import com.nastena.pawsitive.server.favorite.dto.FavoriteResponseDto;
import com.nastena.pawsitive.server.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final AnimalRepository animalRepository;

    public FavoriteService(
            FavoriteRepository favoriteRepository,
            AnimalRepository animalRepository
    ) {
        this.favoriteRepository = favoriteRepository;
        this.animalRepository = animalRepository;
    }

    public void addToFavorites(User user, Long animalId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow();

        if (favoriteRepository.existsByUserAndAnimal(user, animal)) {
            return;
        }

        favoriteRepository.save(new Favorite(user, animal));
    }

    public void removeFromFavorites(User user, Long animalId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow();

        favoriteRepository.findByUserAndAnimal(user, animal)
                .ifPresent(favoriteRepository::delete);
    }

    public List<FavoriteResponseDto> getUserFavoritesDto(User user) {
        return favoriteRepository.findAllByUser(user)
                .stream()
                .map(f -> new FavoriteResponseDto(
                        f.getAnimal().getId(),
                        f.getAnimal().getName(),
                        f.getAnimal().getType(),
                        f.getAnimal().getAge(),
                        f.getAnimal().getShelter().getShelterName()
                ))
                .toList();
    }
}
