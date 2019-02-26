package com.giantnodes.forum.api.conversation;

import com.giantnodes.forum.api.user.User;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Message {

    @DBRef
    private final User sender;
    private final String message;

    public Message(User sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }
}
