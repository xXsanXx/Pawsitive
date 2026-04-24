package com.nastena.pawsitive.server.adoption;

import com.nastena.pawsitive.server.animal.Animal;
import com.nastena.pawsitive.server.shelter.Shelter;
import com.nastena.pawsitive.server.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Long> {
    Optional<AdoptionRequest> findByUserAndAnimal(User user, Animal animal);

    List<AdoptionRequest> findByUser(User user);

    @Query("SELECT a FROM AdoptionRequest  a WHERE a.animal.shelter = :shelter")
    List<AdoptionRequest> findByShelter(@Param("shelter") Shelter shelter);


}
