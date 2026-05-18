package com.smartcampus.application;

import com.smartcampus.domain.user.AppUser;
import com.smartcampus.infrastructure.InMemoryUserRepository;

/**
 * Checks login credentials against users stored in memory.
 */
public class AuthenticationService {
    private final InMemoryUserRepository userRepository;

    public AuthenticationService(InMemoryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean authenticate(String username, String password) {
        AppUser user = userRepository.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
}
