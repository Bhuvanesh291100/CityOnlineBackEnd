package com.city.online.api.service;

import com.city.online.api.dto.request.QuizUserOnboardRequestDto;
import com.city.online.api.model.QuizAnswerSubmit;
import com.city.online.api.model.QuizUserRegistration;
import com.city.online.api.repository.QuizAnswerSubmitRepository;
import com.city.online.api.repository.QuizUserRepository;
import com.city.online.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class QuizUserService {
    @Autowired
    private QuizUserRepository quizUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizAnswerSubmitRepository quizAnswerSubmitRepository;


    /*
     * Create the user object int the database
     * @param userRequestDto
     * @return user
     * */
    public QuizUserRegistration createUser(QuizUserOnboardRequestDto quizUserOnboardRequestDto) {
        QuizUserRegistration quizUserRegistration = new QuizUserRegistration();

        try {
            log.info("Creating the quizUserRegistration with the quizUserRegistration username : {} ", quizUserOnboardRequestDto.getUsername());
            quizUserRegistration.setUsername(quizUserOnboardRequestDto.getUsername());
            quizUserRegistration.setQuizId(quizUserOnboardRequestDto.getQuizId());
            quizUserRegistration.setQuizName(quizUserOnboardRequestDto.getQuizName());
            quizUserRegistration.setQuizDate(quizUserOnboardRequestDto.getQuizDate());
            quizUserRegistration.setFirstName(quizUserOnboardRequestDto.getFirstName());
            quizUserRegistration.setLastName(quizUserOnboardRequestDto.getLastName());
            quizUserRegistration.setEmailId(quizUserOnboardRequestDto.getEmailId());
            quizUserRegistration.setMobileNumber(quizUserOnboardRequestDto.getMobileNumber());
            quizUserRegistration.setParentMobileNumber(quizUserOnboardRequestDto.getParentMobileNumber());
            quizUserRegistration.setSchoolName(quizUserOnboardRequestDto.getSchoolName());
            quizUserRegistration.setQualification(quizUserOnboardRequestDto.getQualification());
            quizUserRegistration.setAddress(quizUserOnboardRequestDto.getAddress());
            //checking if user has already sign up or not

            return quizUserRepository.save(quizUserRegistration);
        } catch (Exception e) {
            log.error("There is an exception while saving the quizUserRegistration {}", e);
            throw e;
        }

    }

    public List<QuizAnswerSubmit> findQuizTakenByUsername(String username) {
        return quizAnswerSubmitRepository.findQuizTakenByUsername(username);
    }

}
