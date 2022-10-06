package com.city.online.api.repository;

import com.city.online.api.model.QuizUserRegistration;
import com.city.online.api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizUserRepository extends MongoRepository<QuizUserRegistration, String> {

    /*@Query("{'userId':?0}")
    QuizUserRegistration findByUserId(String userId);*/

    @Query("{'username':{$ne:null}}")
    List<User> findUsersWhereUsernameIsNotNull();

    Boolean existsByUsername(String username);

    Boolean existsByEmailId(String email);

    Optional<QuizUserRegistration> findByEmailId(String emailId);

    Optional<QuizUserRegistration> findByUsername(String username);
}