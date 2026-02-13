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

    public Long getId() {
        return id;
    }

    public String getShelterName() {
        return shelterName;
    }

    public void setShelterName(String shelterName) {
        this.shelterName = shelterName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
