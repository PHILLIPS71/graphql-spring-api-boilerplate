package com.giantnodes.forum.api.user.graphql;

public class UserInput {

    private String username;
    private String email;
    private String password;
    private String avatar;

    public UserInput(String username, String email, String password, String avatar) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }

    public UserInput() {}


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }


}
