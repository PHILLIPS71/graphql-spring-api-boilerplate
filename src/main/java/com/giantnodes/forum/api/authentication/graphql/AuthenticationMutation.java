package com.giantnodes.forum.api.authentication.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.giantnodes.forum.api.authentication.Authentication;
import com.giantnodes.forum.api.authentication.AuthenticationDao;
import com.giantnodes.forum.api.authentication.graphql.input.CredentialsInput;
import com.giantnodes.forum.api.user.User;
import com.giantnodes.forum.api.user.UserDao;
import com.giantnodes.forum.services.security.SecurityConstants;
import com.giantnodes.forum.services.security.Unsecured;
import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;
import graphql.servlet.GraphQLContext;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class AuthenticationMutation implements GraphQLMutationResolver {

    @Autowired
    private AuthenticationDao dao;

    @Autowired
    private UserDao users;

    @Unsecured
    public Authentication authenticate(CredentialsInput credentials, DataFetchingEnvironment environment) {
        GraphQLContext context = environment.getContext();
        User user = users.getByEmail(credentials.getEmail());

        if (!BCrypt.checkpw(credentials.getPassword(), user.getPassword())) {
            throw new GraphQLException("Incorrect Email or Password");
        }

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), null));
        String token = Jwts.builder()
                .setSubject(user.getId())
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET)
                .compact();

        Authentication auth = dao.getByUserId(user.getId());
        if(auth == null) {
            auth = dao.create(new Authentication(user, token));
        }

        return auth;
    }



}
