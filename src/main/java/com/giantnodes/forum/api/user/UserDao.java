package com.giantnodes.forum.api.user;

import com.giantnodes.forum.api.API;
import com.giantnodes.forum.api.user.graphql.UserInput;
import com.giantnodes.forum.services.graphql.exceptions.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class UserDao implements API<User, UserInput> {

    @Autowired
    private UserRepository repository;

    @Transactional
    public User create(String username, String email, String password) {
        return repository.save(new User(username, email, password));
    }

    @Override
    @Transactional
    public User delete(String id) {
        User user = repository.findById(id).get();
        repository.delete(repository.findById(id).get());
        return user;
    }

    @Override
    @Transactional
    public User update(String id, UserInput input) {
        User user = repository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
        user.merge(input);
        return repository.save(user);
    }

    @Override
    @Transactional
    public User get(String id) {
        return repository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
    }

    @Override
    @Transactional
    public List<User> all() {
        return repository.findAll();
    }
}
