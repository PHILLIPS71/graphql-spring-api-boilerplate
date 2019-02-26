package com.giantnodes.fish.api.authentication.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.giantnodes.fish.api.authentication.Authentication;
import com.giantnodes.fish.api.authentication.AuthenticationDao;
import com.giantnodes.fish.api.authentication.component.connection.AuthConnection;
import com.giantnodes.fish.api.authentication.component.connection.AuthConnectionDao;
import com.giantnodes.fish.api.authentication.component.connection.AuthConnectionHelper;
import com.giantnodes.fish.api.authentication.graphql.input.CredentialsInput;
import com.giantnodes.fish.api.user.User;
import com.giantnodes.fish.api.user.UserDao;
import com.giantnodes.fish.services.security.SecurityConstants;
import com.giantnodes.fish.services.security.Unsecured;
import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;
import graphql.servlet.GraphQLContext;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class AuthenticationMutation implements GraphQLMutationResolver {

    @Autowired
    private AuthenticationDao dao;

    @Autowired
    private AuthConnectionDao connections;

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

        AuthConnection connection = connections
                .create(AuthConnectionHelper.parse(context.getHttpServletRequest().get().getHeader("User-Agent")));
        AuthConnectionHelper.addConnection(auth, connection);
        return dao.save(auth);
    }



}
