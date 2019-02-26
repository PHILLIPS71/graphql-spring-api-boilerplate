package com.giantnodes.fish.api.authentication;

import com.giantnodes.fish.api.authentication.component.connection.AuthConnection;
import com.giantnodes.fish.api.user.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "authentication")
public class Authentication {

    @Id
    private String id;
    @DBRef
    private final User user;
    @DBRef
    private List<AuthConnection> connections;

    private String token;

    public Authentication(User user, String token) {
        this.user = user;
        this.token = token;
        this.connections = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public List<AuthConnection> getConnections() {
        return connections;
    }
}
