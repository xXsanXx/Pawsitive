package com.nastena.pawsitive.server.animal;

import com.nastena.pawsitive.dto.AnimalBreed;
import com.nastena.pawsitive.dto.AnimalGender;
import com.nastena.pawsitive.dto.AnimalType;
import com.nastena.pawsitive.server.shelter.Shelter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "shelter_id", nullable = false)
    private Shelter shelter;

    @Setter
    private String name;

    @Setter
    private AnimalType type;

    @Setter
    private AnimalBreed breed;

    @Setter
    private Long birthDate;

    @Setter
    private String description;

    @Setter
    @Enumerated(EnumType.STRING)
    private AnimalGender gender;

    public Animal() {}

    public Animal(Shelter shelter) {
        this.shelter = shelter;
    }


}
