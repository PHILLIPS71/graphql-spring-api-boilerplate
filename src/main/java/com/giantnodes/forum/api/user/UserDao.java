package com.giantnodes.forum.api.user;

import com.giantnodes.forum.api.user.graphql.UserInput;
import com.giantnodes.forum.services.graphql.exceptions.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public User update(String id, UserInput input) {
        User user = repository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
        user.merge(input);
        return repository.save(user);
    }

    @Transactional
    public User get(String id) {
        return repository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
    }

    @Transactional
    public List<User> all() {
        return repository.findAll();
    }

    //    public boolean authenticate(String attempt) {
    //        return BCrypt.checkpw(attempt, getPassword());
    //    }
}
