package com.nastena.pawsitive.server.user;

import com.nastena.pawsitive.server.account.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false, unique = true)
    private Account account;

    @NotNull
    private String name;

    private Long birthDate;

    @Column(nullable = false)
    private String profession = "";

    @Column(nullable = false)
    private String currentPets = "";

    @Column(nullable = false)
    private String previousPets = "";

    @Column(nullable = false)
    private String feedingExperience = "";

    @Column(nullable = false)
    private String vaccination = "";

    @Column(nullable = false)
    private String reason = "";

    @Column(nullable = false)
    private String petCareWhenAway = "";

    @Column(nullable = false)
    private String problemCharacter = "";

    @Column(nullable = false)
    private String healthIssues = "";

    @Column(nullable = false)
    private String additionalInfo = "";

    @Column(nullable = false)
    private String phone = "";

    public User() {}

}
