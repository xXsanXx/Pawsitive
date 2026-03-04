package com.nastena.pawsitive.server.account;

import com.nastena.pawsitive.server.account.dto.AccountRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = "passwordHash")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private AccountRole role;

    public Account(String email, String passwordHash, AccountRole role) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }
}
