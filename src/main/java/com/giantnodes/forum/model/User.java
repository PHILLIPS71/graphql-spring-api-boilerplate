package com.giantnodes.forum.model;

import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    private final String id;
    private final String username;
    private final String email;
    private String password;
    private String avatar;

    public User(String username, String email, String password) {
        this.id = new ObjectId().toString();
        this.username = username;
        this.email = email;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(9));
        this.avatar = null;
        System.out.println("created");
        System.out.println(authenticate("password"));
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean authenticate(String attempt) {
        return BCrypt.checkpw(attempt, getPassword());
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
