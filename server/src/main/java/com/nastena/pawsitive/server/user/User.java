package com.nastena.pawsitive.server.user;

import com.nastena.pawsitive.server.account.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
public class User {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @OneToOne
    @JoinColumn(name = "account_id", nullable = false, unique = true)
    private Account account;

    @Getter
    @Setter
    private String name;

    public User() {}

    public User(Account account) {
        this.account = account;
    }

}
