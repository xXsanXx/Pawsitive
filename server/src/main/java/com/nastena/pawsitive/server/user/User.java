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
    private String phone;

    public User() {}

}
