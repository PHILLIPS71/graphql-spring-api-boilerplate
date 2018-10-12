package com.giantnodes.forum.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.giantnodes.forum.model.User;
import com.giantnodes.forum.services.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserQuery implements GraphQLQueryResolver {

    @Autowired
    private UserDao dao;


    public List<User> users() {
        return dao.all();
    }

    public List<User> get(Optional<String> username) {
        return dao.get(username);
    }
}