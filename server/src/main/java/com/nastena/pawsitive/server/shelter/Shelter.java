package com.nastena.pawsitive.server.shelter;

import com.nastena.pawsitive.server.account.Account;
import jakarta.persistence.*;

@Entity
@Table(name = "shelters")
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false, unique = true)
    private Account account;

    private String shelterName;
    private String address;
    private String phone;

    @Column(length = 1000)
    private String info;

    public Shelter() {}

    public Shelter(Account account) {
        this.account = account;
    }

}
