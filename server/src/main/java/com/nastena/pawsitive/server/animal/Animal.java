package com.nastena.pawsitive.server.animal;

import com.nastena.pawsitive.server.shelter.Shelter;
import jakarta.persistence.*;

@Entity
@Table(name = "animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shelter_id", nullable = false)
    private Shelter shelter;

    private String animalName;
    private String type;
    private String breed;
    private Integer age;

    @Column(length = 1000)
    private String healthInfo;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    public enum Gender {
        MALE, FEMALE
    }

    public Animal() {}

    public Animal(Shelter shelter) {
        this.shelter = shelter;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getHealthInfo() {
        return healthInfo;
    }

    public void setHealthInfo(String healthInfo) {
        this.healthInfo = healthInfo;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }


}
