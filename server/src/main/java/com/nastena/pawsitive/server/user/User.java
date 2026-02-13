package com.nastena.pawsitive.server.user;

import com.nastena.pawsitive.server.account.Account;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false, unique = true)
    private Account account;

    private String username;

    public User() {}

    public User(Account account) {
        this.account = account;
    }
}
