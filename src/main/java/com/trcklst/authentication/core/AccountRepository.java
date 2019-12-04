package com.trcklst.authentication.core;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, Integer> {

    Optional<Account> findByUsername(String username);
}
