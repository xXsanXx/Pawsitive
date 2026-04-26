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

    @NotNull
    private String profession;

    @NotNull
    private String currentPets;

    @NotNull
    private String previousPets;

    @NotNull
    private String feedingExperience;

    @NotNull
    private String vaccination;

    @NotNull
    private String reason;

    @NotNull
    private String petCareWhenAway;

    @NotNull
    private String problemCharacter;

    @NotNull
    private String healthIssues;

    @NotNull
    private String additionalInfo;

    @NotNull
    private String phone;

    public User() {}

}
