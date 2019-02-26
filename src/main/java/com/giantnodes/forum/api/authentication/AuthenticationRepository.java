package com.giantnodes.fish.api.authentication;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository  extends MongoRepository<Authentication, String> {

    Authentication getAuthenticationByUser_Id(String id);
}
