package com.giantnodes.forum.api.user;

public class UserAuth {

    private final User user;
    private final String token;

    public UserAuth(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public User getUser() {
        return this.user;
    }

    public String getToken() {
        return token;
    }

}
