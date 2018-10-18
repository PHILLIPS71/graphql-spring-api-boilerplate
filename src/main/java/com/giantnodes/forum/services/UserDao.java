package com.giantnodes.forum.services;

import com.giantnodes.forum.model.User;
import com.giantnodes.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class UserDao {

    @Autowired
    private UserRepository repository;

    @Transactional
    public User create(String username, String email, String password) {
        User user = new User(username, email, password);
        user.setPassword(password);
        return repository.save(user);
    }

    @Transactional
    public User delete(String id) {
        User user = repository.findById(id).get();
        repository.delete(user);
        return user;
    }

    @Transactional
    public List<User> get(Optional<String> username) {
        return repository.findByUsernameLike(username.get());
    }

    @Transactional
    public List<User> all() {
        return repository.findAll();
    }

    //    public boolean authenticate(String attempt) {
    //        return BCrypt.checkpw(attempt, getPassword());
    //    }
}
