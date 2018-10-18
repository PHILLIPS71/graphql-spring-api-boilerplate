package com.giantnodes.forum.model;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.DigestUtils;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String username;
    private String email;
    private String password;
    private String avatar;

    @CreatedDate
    private DateTime createdAt;

    @LastModifiedDate
    private DateTime modifiedAt;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(9));
        this.avatar = "https://gravatar.com/avatar/" + DigestUtils.md5DigestAsHex(email.getBytes()) + "?d=identicon";
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public DateTime getModifiedAt() {
        return modifiedAt;
    }
}
