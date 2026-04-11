package com.nastena.pawsitive.server.favorite;

import com.nastena.pawsitive.server.animal.Animal;
import com.nastena.pawsitive.server.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser(User user);

    Optional<Favorite> findByUserAndAnimal(User user, Animal animal);
    Optional<Favorite> findByUserAndAnimalId(User user, Long animalId);

    void deleteByAnimalAndUser(Animal animal, User user);
}