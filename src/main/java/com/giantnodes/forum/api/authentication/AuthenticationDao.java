package com.giantnodes.forum.api.authentication;

import com.giantnodes.forum.api.API;
import com.giantnodes.forum.api.authentication.graphql.input.AuthenticationInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthenticationDao implements API<Authentication, AuthenticationInput> {

    @Autowired
    private AuthenticationRepository repository;

    @Override
    public Authentication create(Authentication auth) {
        return repository.save(auth);
    }

    @Override
    public Authentication delete(String id) {
        return null;
    }

    @Override
    public Authentication update(String id, AuthenticationInput input) {
        return null;
    }

    @Override
    public Authentication get(String id) {
        return null;
    }

    @Override
    public List<Authentication> all() {
        return null;
    }

    public Authentication getByUserId(String id) {
        return repository.getAuthenticationByUser_Id(id);
    }
}
