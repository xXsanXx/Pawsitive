package com.nastena.pawsitive.server.shelter;

import com.nastena.pawsitive.server.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    Optional<Shelter> findByAccount(Account account);
}
