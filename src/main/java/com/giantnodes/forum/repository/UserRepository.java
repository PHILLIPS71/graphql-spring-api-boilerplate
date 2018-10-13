package com.giantnodes.forum.repository;

import com.giantnodes.forum.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByUsernameLike(String username);

    Optional<User> findById(String id);

}