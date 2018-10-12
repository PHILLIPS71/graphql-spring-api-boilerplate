package com.giantnodes.forum.services;

import com.giantnodes.forum.model.User;
import com.giantnodes.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDao {

    @Autowired
    private UserRepository repository;

    public User create(String username, String email, String password) {
        User user = new User(username, email, password);
        user.setPassword(password);
        return repository.save(user);
    }

    public User delete(String id) {
        User user = repository.findById(id).get();
        repository.delete(user);
        return user;
    }

    public List<User> get(Optional<String> username) {
        return repository.findByUsernameLike(username.get());
    }

    public List<User> all() {
        return repository.findAll();
    }
}
