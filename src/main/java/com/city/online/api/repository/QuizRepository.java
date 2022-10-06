package com.city.online.api.repository;

import com.city.online.api.model.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends MongoRepository<Quiz, String> {
    Optional<Quiz> findByQuizName(String qName);
    Optional<Quiz> findByQuizDate(String qDate);
    @Query("{'$and':[{ 'quizStatus':?0}, {'tenantId':?1}]}")
    List<Quiz> findByStatusAndTenant(String status, String tenant);
}
