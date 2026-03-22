package com.nastena.pawsitive.server.shelter;

import com.nastena.pawsitive.server.account.Account;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Table(name = "shelters")
public class Shelter {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @OneToOne
    @JoinColumn(name = "account_id", nullable = false, unique = true)
    private Account account;

    @Setter
    @Getter
    private String name;

    @Getter
    @Column(nullable = false)
    private String address = "";

    @Getter
    @Column(nullable = false)
    private String phone = "";

    @Getter
    @Column(length = 1000, nullable = false)
    private String info = "";

    public Shelter() {}

    public Shelter(Account account) {
        this.account = account;
        this.address = "";
        this.phone = "";
        this.info = "";
    }

    public void setAddress(String address) {
        this.address = address != null ? address : "";
    }

    public void setPhone(String phone) {
        this.phone = phone != null ? phone : "";
    }

    public void setInfo(String info) {
        this.info = info != null ? info : "";
    }

}
