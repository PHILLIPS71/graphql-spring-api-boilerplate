package com.giantnodes.forum.api.user.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.giantnodes.forum.api.user.UserAuth;
import com.giantnodes.forum.api.user.User;
import com.giantnodes.forum.api.user.UserDao;
import com.giantnodes.forum.api.user.graphql.input.CredentialsInput;
import com.giantnodes.forum.api.user.graphql.input.UserInput;
import com.giantnodes.forum.services.security.SecurityConstants;
import com.giantnodes.forum.services.security.Unsecured;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class UserMutation implements GraphQLMutationResolver {

    @Autowired
    private UserDao dao;

    public User create(String username, String email, String password) {
        return dao.create(new User(username, email, password));
    }

    public User delete(String id) {
        return dao.delete(id);
    }

    public User update(String id, UserInput input) {
        return dao.update(id, input);
    }

    @Unsecured
    public UserAuth login(CredentialsInput input) {
        User user = dao.getByEmail(input.getEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ZonedDateTime expiration = ZonedDateTime.now(ZoneOffset.UTC).plus(SecurityConstants.EXPIRATION_TIME, ChronoUnit.MILLIS);
        String token = Jwts.builder().setSubject(user.getId())
                .setExpiration(Date.from(expiration.toInstant()))
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET)
                .compact();
        return new UserAuth(user, token);
    }

}
