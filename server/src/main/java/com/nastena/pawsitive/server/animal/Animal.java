package com.nastena.pawsitive.server.animal;

import com.nastena.pawsitive.dto.AnimalBreed;
import com.nastena.pawsitive.dto.AnimalGender;
import com.nastena.pawsitive.dto.AnimalType;
import com.nastena.pawsitive.server.shelter.Shelter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    @Enumerated(EnumType.STRING)
    private AnimalType type;

    @Setter
    @Enumerated(EnumType.STRING)
    private AnimalBreed breed;

    @Setter
    private Long birthDate;

    @Setter
    private String description;

    @Setter
    @Enumerated(EnumType.STRING)
    private AnimalGender gender;

    // ------PHOTOS------
    @Setter
    @ElementCollection
    @CollectionTable(name = "animal_photos", joinColumns = @JoinColumn(name = "animal_id"))
    @Column(name = "photo_url")
    private List<String> photoUrls = new ArrayList<>();

    @Setter
    @ElementCollection
    @CollectionTable(name = "animal_vet_passports", joinColumns = @JoinColumn(name = "animal_id"))
    @Column(name = "vet_passport_url")
    private List<String> vetPassportUrls = new ArrayList<>();

    public Animal() {}

    public Animal(Shelter shelter) {
        this.shelter = shelter;
    }


}
