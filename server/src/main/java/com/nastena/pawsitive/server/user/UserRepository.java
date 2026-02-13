package com.nastena.pawsitive.server.user;

import com.nastena.pawsitive.server.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAccount(Account account);
}
