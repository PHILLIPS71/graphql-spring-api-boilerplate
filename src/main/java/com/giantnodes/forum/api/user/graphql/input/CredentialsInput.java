package com.giantnodes.forum.api.user.graphql.input;

public class CredentialsInput {

    private String email;
    private String password;

    public CredentialsInput(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public CredentialsInput() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
