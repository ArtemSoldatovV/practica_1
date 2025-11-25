package org.example.UserService.token;

import org.example.UserService.User;

public class TokenForUser {
    private String token;
    private User user;

    public TokenForUser(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public boolean cherToken(String token){
        return this.token == token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
