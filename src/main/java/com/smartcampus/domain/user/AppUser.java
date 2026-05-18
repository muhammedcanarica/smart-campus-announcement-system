package com.smartcampus.domain.user;

/**
 * Represents a user who can log in to the console application.
 */
public class AppUser {
    private final String username;
    private final String password;
    private final UserRole role;

    public AppUser(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }
}
