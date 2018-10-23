package com.giantnodes.forum.api.user;

public class UserInput {

    private String id;
    private String username;
    private String email;
    private String password;
    private String avatar;

    public UserInput(String id, String username, String email, String password, String avatar) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }

    public UserInput() {}

    public String getId() {
        return id;
    }

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
