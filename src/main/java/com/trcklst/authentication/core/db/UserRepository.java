package com.trcklst.authentication.core.db;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
