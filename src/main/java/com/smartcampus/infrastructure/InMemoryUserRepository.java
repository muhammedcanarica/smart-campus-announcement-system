package com.smartcampus.infrastructure;

import com.smartcampus.domain.user.UserObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Very small in-memory repository used instead of a database.
 */
public class InMemoryUserRepository {
    private final List<UserObserver> users = new ArrayList<>();

    public void addUser(UserObserver user) {
        users.add(user);
    }

    public List<UserObserver> findAll() {
        return Collections.unmodifiableList(users);
    }
}
