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

    @Enumerated(EnumType.STRING)
    private AdoptionStatus status;




    public AdoptionRequest() {}

}
