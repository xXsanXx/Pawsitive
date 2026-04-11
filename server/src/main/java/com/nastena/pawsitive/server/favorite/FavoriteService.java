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

    public void addToFavorite(User user, Animal animal) {

        Favorite favorite = new Favorite(user, animal);
        favoriteRepository.save(favorite);
    }

    public List<Favorite> getUserFavorites(User user) {
        return favoriteRepository.findByUser(user);
    }

    public void removeFromFavorite(User user, Animal animal) {
        favoriteRepository.deleteByAnimalAndUser(user, animal);
    }


}
