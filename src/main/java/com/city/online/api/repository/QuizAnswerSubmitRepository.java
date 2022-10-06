package com.city.online.api.repository;

import com.city.online.api.dto.request.QuizAnswerSubmitDto;
import com.city.online.api.model.QuizAnswerSubmit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizAnswerSubmitRepository extends MongoRepository<QuizAnswerSubmit,String> {
      @Query("{'quizName':?0}")
      Page<QuizAnswerSubmit> findToppers(String quizName, Pageable pageable);

      @Query("{'$and':[{ 'quizId':?0}, {'username':?1}]}")
      QuizAnswerSubmitDto findByQuizIdAndUsername(String quizId, String username);

      @Query("{'username':?0}")
      List<QuizAnswerSubmit> findQuizTakenByUsername(String username);
}
