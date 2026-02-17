package com.nastena.pawsitive.server.favorite;

import com.nastena.pawsitive.server.animal.Animal;
import com.nastena.pawsitive.server.user.User;
import jakarta.persistence.*;

@Entity
@Table(
        name = "favorites",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "animal_id"})
        }
)
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "animal_id")
    private Animal animal;

    public Favorite() {}

    public Favorite(User user, Animal animal) {
        this.user = user;
        this.animal = animal;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Animal getAnimal() {
        return animal;
    }
}