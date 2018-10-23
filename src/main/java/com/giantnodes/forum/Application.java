package com.giantnodes.forum;

import com.giantnodes.forum.api.user.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        User user = new User("PHIL", "mail@gmail.com", "password");
        User target = new User("PHILLIPS_71", "jordypee27@gmail.com", "password");

        //user.merge(target);
    }
}