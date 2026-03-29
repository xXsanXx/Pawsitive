package com.nastena.pawsitive.server.animal;

import com.nastena.pawsitive.server.shelter.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findAnimalsByShelter(Shelter shelter );
}
