package com.nastena.pawsitive.server.animal;

import com.nastena.pawsitive.dto.AnimalGender;
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
    private String animalName;

    @Setter
    private String type;

    @Setter
    private String breed;

    @Setter
    private Integer age;

    @Setter
    @Column(length = 1000)
    private String healthInfo;

    @Setter
    @Enumerated(EnumType.STRING)
    private AnimalGender gender;

    public Animal() {}

    public Animal(Shelter shelter) {
        this.shelter = shelter;
    }


}
