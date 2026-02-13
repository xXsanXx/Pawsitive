package com.nastena.pawsitive.server.user;

import com.nastena.pawsitive.server.account.Account;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(Account account) {
        User user = new User(account);
        return userRepository.save(user);

    }
}
