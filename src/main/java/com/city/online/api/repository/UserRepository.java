package com.city.online.api.repository;

import com.city.online.api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);

    @Query("{username:?0}")
    User findUserByUsername(String username);

    Boolean existsByUsername(String username);
    Boolean existsByEmailId(String email);
}