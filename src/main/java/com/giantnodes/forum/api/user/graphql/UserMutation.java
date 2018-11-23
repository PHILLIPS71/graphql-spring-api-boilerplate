package com.giantnodes.forum.api.user.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.giantnodes.forum.api.user.UserAuth;
import com.giantnodes.forum.api.user.User;
import com.giantnodes.forum.api.user.UserDao;
import com.giantnodes.forum.api.user.graphql.input.CredentialsInput;
import com.giantnodes.forum.api.user.graphql.input.UserInput;
import com.giantnodes.forum.services.security.SecurityConstants;
import com.giantnodes.forum.services.security.Unsecured;
import com.giantnodes.forum.utility.resources.FileUpload;
import com.giantnodes.forum.utility.resources.ResourceDirectory;
import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;
import graphql.servlet.GraphQLContext;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

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

    @Unsecured
    public User update(String id, UserInput input, DataFetchingEnvironment environment) {
        GraphQLContext context = environment.getContext();

        if (!input.getAvatar().isEmpty()) {
            FileUpload avatar = new FileUpload(environment.getContext(), ResourceDirectory.STORAGE_AVATAR, id);
            input.setAvatar("http://localhost:8080/" + avatar.getLocation().getDirectory() + avatar.getFile().getName());
        }

        return dao.update(id, input);
    }

    @Unsecured
    public UserAuth login(CredentialsInput input) {
        User user = dao.getByEmail(input.getEmail());

        if (!BCrypt.checkpw(input.getPassword(), user.getPassword())) {
            throw new GraphQLException("Incorrect Email or Password");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = Jwts.builder().setSubject(user.getId())
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET)
                .compact();
        return new UserAuth(user, token);
    }
}
