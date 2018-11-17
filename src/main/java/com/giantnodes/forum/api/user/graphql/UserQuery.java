package com.giantnodes.forum.api.user.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.giantnodes.forum.api.user.User;
import com.giantnodes.forum.api.user.UserAuth;
import com.giantnodes.forum.api.user.UserDao;
import com.giantnodes.forum.services.security.SecurityConstants;
import com.giantnodes.forum.services.security.Unsecured;
import graphql.GraphQLError;
import graphql.GraphQLException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
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

    @Unsecured
    public UserAuth verify(String token) {
        String id;
        try {
            id = Jwts.parser().setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                    .getBody().getSubject();
        } catch (SignatureException e) {
            throw new GraphQLException("Invalid JWT Token");
        }

        return new UserAuth(dao.get(id), token);
    }
}