package com.giantnodes.forum.api.authentication.graphql.input;

public class CredentialsInput {

    private String email;
    private String password;

    public CredentialsInput(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
