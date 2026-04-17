package com.nastena.pawsitive.server.adoption;

import com.nastena.pawsitive.dto.AdoptionStatus;
import com.nastena.pawsitive.server.animal.Animal;
import com.nastena.pawsitive.server.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "adoption_requests")
public class AdoptionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Animal animal;

    private String fullName;

    private String age;

    private String profession;

    private String phone;

    @Enumerated(EnumType.STRING)
    private AdoptionStatus status;


    public AdoptionRequest() {}

    public AdoptionRequest(User user, Animal animal, String fullName, String age, String profession, String phone) {
        this.user = user;
        this.animal = animal;
        this.fullName = fullName;
        this.age = age;
        this.profession = profession;
        this.phone = phone;
    }
}
