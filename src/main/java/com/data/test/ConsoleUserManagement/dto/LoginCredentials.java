package com.data.test.ConsoleUserManagement.dto;

public class LoginCredentials {
    private final String username;
    private final PasswordHolder password;

    public LoginCredentials(String username, PasswordHolder password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public PasswordHolder getPassword() {
        return password;
    }
}
