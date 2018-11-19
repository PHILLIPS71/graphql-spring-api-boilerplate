package com.giantnodes.forum.api.user.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.giantnodes.forum.api.user.UserAuth;
import com.giantnodes.forum.api.user.User;
import com.giantnodes.forum.api.user.UserDao;
import com.giantnodes.forum.api.user.graphql.input.CredentialsInput;
import com.giantnodes.forum.api.user.graphql.input.UserInput;
import com.giantnodes.forum.services.security.SecurityConstants;
import com.giantnodes.forum.services.security.Unsecured;
import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;
import graphql.servlet.GraphQLContext;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.io.FileUtils;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        if (context.getFiles().isPresent()) {
            List<Part> parts = context.getFiles().get().values()
                    .stream()
                    .flatMap(Collection::stream)
                    .filter(part -> part.getContentType() != null)
                    .collect(Collectors.toList());

            for (Part part : parts) {
                try {
                    String name = new ObjectId().toHexString() + "." + part.getContentType().split("/")[1];
                    FileUtils.copyInputStreamToFile(part.getInputStream(), new File("storage/" + name));
                    input.setAvatar("http://localhost:8080/storage/" + name);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
