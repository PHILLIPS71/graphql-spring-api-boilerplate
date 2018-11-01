package com.giantnodes.forum.api.user;

import com.giantnodes.forum.api.API;
import com.giantnodes.forum.api.user.graphql.UserInput;
import com.giantnodes.forum.services.graphql.exceptions.IdNotFoundException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;

@Component
public class UserDao implements API<User, UserInput> {

    @Autowired
    private UserRepository repository;

    @Override
    @Transactional
    public User create(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(9)));
        user.setAvatar("https://gravatar.com/avatar/" + DigestUtils.md5DigestAsHex(user.getEmail().getBytes()) + "?d=identicon");
        return repository.save(user);
    }

    @Override
    @Transactional
    public User delete(String id) {
        User user = repository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
        repository.delete(user);
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
