package com.nastena.pawsitive.server.adoption;

import com.nastena.pawsitive.server.animal.Animal;
import com.nastena.pawsitive.server.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Long> {
    Optional<AdoptionRequest> findByUserAndAnimal(User user, Animal animal);
}
