package com.nastena.pawsitive.server.animal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findByShelterId(Long shelterId);

    @Query("""
            SELECT a FROM Animal a
            WHERE (:type IS NULL OR a.type = :type)
                AND (:gender IS NULL OR a.gender = :gender)
                AND (:minAge IS NULL OR a.age >= :minAge)
                AND (:maxAge IS NULL OR a.age <= :maxAge)
            """)

    List<Animal> findWithFilters(
            @Param("type") String type,
            @Param("gender") Animal.Gender gender,
            @Param("minAge") Integer minAge,
            @Param("maxAge") Integer maxAge
    );
}
