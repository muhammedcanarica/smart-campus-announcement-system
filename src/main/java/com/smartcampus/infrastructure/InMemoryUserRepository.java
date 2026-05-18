package com.smartcampus.infrastructure;

import com.smartcampus.domain.user.AppUser;
import com.smartcampus.domain.user.UserRole;
import com.smartcampus.domain.user.UserObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Very small in-memory repository used instead of a database.
 */
public class InMemoryUserRepository {
    private final List<UserObserver> observerUsers = new ArrayList<>();
    private final List<AppUser> appUsers = new ArrayList<>();

    public InMemoryUserRepository() {
        appUsers.add(new AppUser("admin", "1234", UserRole.ADMIN));
    }

    public void addUser(UserObserver user) {
        observerUsers.add(user);
    }

    public List<UserObserver> findAll() {
        return Collections.unmodifiableList(observerUsers);
    }

    public AppUser findByUsername(String username) {
        for (AppUser user : appUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
