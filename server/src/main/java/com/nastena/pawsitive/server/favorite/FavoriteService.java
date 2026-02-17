package com.nastena.pawsitive.server.favorite;

import com.nastena.pawsitive.server.animal.Animal;
import com.nastena.pawsitive.server.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public void addToFavorites(User user, Animal animal) {
        if (favoriteRepository.existsByUserAndAnimal(user, animal)) {
            return; // уже в избранном — ничего не делаем
        }
        favoriteRepository.save(new Favorite(user, animal));
    }

    public void removeFromFavorites(User user, Animal animal) {
        favoriteRepository.findByUserAndAnimal(user, animal)
                .ifPresent(favoriteRepository::delete);
    }

    public List<Favorite> getUserFavorites(User user) {
        return favoriteRepository.findAllByUser(user);
    }
}