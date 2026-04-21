package com.nastena.pawsitive.server.favorite;

import com.nastena.pawsitive.server.animal.Animal;
import com.nastena.pawsitive.server.user.User;
import com.nastena.pawsitive.server.user.UserAnimalsQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    private UserAnimalsQueueService animalsQueueService;

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;

    }

    public void addToFavorite(User user, Animal animal) {

        Favorite favorite = new Favorite(user, animal);
        favoriteRepository.save(favorite);

        animalsQueueService.removeAnimalFromQueue(user, animal.getId());

    }

    public List<Favorite> getUserFavorites(User user) {
        return favoriteRepository.findByUser(user);
    }

    @Transactional
    public void removeFromFavorite(Animal animal, User user) {
        favoriteRepository.deleteByAnimalAndUser(animal, user);
    }


}
