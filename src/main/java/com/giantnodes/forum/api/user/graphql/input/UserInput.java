package com.giantnodes.forum.api.user.graphql.input;

import org.joda.time.DateTime;
import org.mindrot.jbcrypt.BCrypt;

public class UserInput {

    private String email;
    private String password;
    private String avatar;
    private DateTime seen;

    public UserInput(String email, String password, String avatar, String seen) {
        this.email = email;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(9));;
        this.avatar = avatar;
        this.seen = DateTime.parse(seen);
    }

    public UserInput() {

    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(9));
    }

    public String getAvatar() {
        return avatar;
    }

    public  DateTime getSeen() {
        return seen;
    }


}
