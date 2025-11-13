package org.example.UserService.token;

import org.example.UserService.User;

public class TokenForUser {
    private String token;
    private User user;

    public TokenForUser(String token, User user) {
        this.token = token;
        this.user = user;
    }

    // геттеры
    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
