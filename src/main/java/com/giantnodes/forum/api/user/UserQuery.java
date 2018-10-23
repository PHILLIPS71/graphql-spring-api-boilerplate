package com.giantnodes.forum.api.user;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserQuery implements GraphQLQueryResolver {

    @Autowired
    private UserDao dao;


    public List<User> users() {
        return dao.all();
    }

    public User get(String id) {
        return dao.get(id);
    }
}