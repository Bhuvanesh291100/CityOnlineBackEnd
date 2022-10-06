package com.city.online.api.service;

import com.city.online.api.constant.AppConstants;
import com.city.online.api.dto.request.QuestionDto;
import com.city.online.api.dto.request.QuizAnswerSubmitDto;
import com.city.online.api.dto.request.QuizRequestDto;
import com.city.online.api.exception.BusinessException;
import com.city.online.api.model.Quiz;
import com.city.online.api.model.QuizAnswerSubmit;
import com.city.online.api.model.enums.Status;
import com.city.online.api.model.pojo.Question;
import com.city.online.api.repository.QuestionRepository;
import com.city.online.api.repository.QuizAnswerSubmitRepository;
import com.city.online.api.repository.QuizRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class QuizService {
    private QuestionRepository questionRepository;

    @Autowired
    private QuizAnswerSubmitRepository quizAnswerSubmitRepository;
    @Autowired
    private QuizRepository quizRepository;
    private Question question;
    private Quiz quiz;

    public Quiz createQuiz(QuizRequestDto quizRequestDto) {
        Quiz quiz = new Quiz();
        Integer questionNumber = new Integer(0);
        try {
            quiz.setQuizName(quizRequestDto.getQuizName());
            quiz.setQuizDescription(quizRequestDto.getQuizDescription());
            quiz.setQuizQualification(quizRequestDto.getQuizQualification());
            quiz.setQuizDate(quizRequestDto.getQuizDate());
            quiz.setStartTime(quizRequestDto.getStartTime());
            quiz.setEndTime(quizRequestDto.getEndTime());
            quiz.setQuizMarks(quizRequestDto.getQuizMarks());
            quiz.setQuizSubject(quizRequestDto.getQuizSubject());
            quiz.setQuizType(quizRequestDto.getQuizType());
            quiz.setQuizTotalQuestion(quizRequestDto.getQuizTotalQuestion());
            quiz.setQuizStatus(Status.UNDER_REVIEW);
            quiz.setTenantId(quizRequestDto.getTenantId());
            //addQuestionNumber(quizRequestDto.getQuestions());
            // create the question numbers
            for (QuestionDto questionDto: quizRequestDto.getQuestions()) {
                questionDto.setQuestionNumber(questionNumber++);
            }
            quiz.setQuestions(quizRequestDto.getQuestions());
            return quizRepository.save(quiz);
        } catch (Exception e) {
            throwException(AppConstants.QUIZ_VALIDATION_ERROR_CODE, AppConstants.QUIZ_VALIDATION_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
            return null;
        }
    }


    private void throwException(String code, String errorMessage, HttpStatus httpStatus) {
        throw new BusinessException(code, errorMessage, httpStatus, null);
    }


    public Quiz getQuizById(String qId) {
        Optional<Quiz> quiz1 = quizRepository.findById(qId);

        if (quiz1.isPresent()) {
            Quiz quiz =  quiz1.get();
            return quiz;
        }
        else
            throwException(AppConstants.QUIZ_VALIDATION_ERROR_CODE, AppConstants.QUIZ_VALIDATION_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        return null;
    }

    public Quiz getByQuizName(String qName) {
        Optional<Quiz> quiz1 = quizRepository.findByQuizName(qName);
        if (quiz1.isPresent()) {
                    Quiz quiz =  quiz1.get();
                    return quiz;
            }
            else
              throwException(AppConstants.QUIZ_VALIDATION_ERROR_CODE, AppConstants.QUIZ_VALIDATION_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
            return null;
    }

    public Quiz getByQuizDate(String qDate) {
        Optional<Quiz> quiz1 = quizRepository.findByQuizDate(qDate);
        if (quiz1.isPresent()) {
            Quiz quiz =  quiz1.get();
            return quiz;
        } else
            throwException(AppConstants.QUIZ_VALIDATION_ERROR_CODE, AppConstants.QUIZ_VALIDATION_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        return null;
    }

    public QuizAnswerSubmit saveQuizAnswerSubmit(QuizAnswerSubmitDto quizAnswerSubmitDto) {
        QuizAnswerSubmit quizStoreAnswer = new QuizAnswerSubmit();
        try {
            quizStoreAnswer.setUsername(quizAnswerSubmitDto.getUsername());
            quizStoreAnswer.setQuizName(quizAnswerSubmitDto.getQuizName());
            quizStoreAnswer.setQuizId(quizAnswerSubmitDto.getQuizId());
            quizStoreAnswer.setTimeTakenToSubmit(quizAnswerSubmitDto.getTimeTakenToSubmit());
            quizStoreAnswer.setScore(quizAnswerSubmitDto.getScore());
            quizStoreAnswer.setQuestionAndOption(quizAnswerSubmitDto.getQuestionAndOption());
            return quizAnswerSubmitRepository.save(quizStoreAnswer);
        } catch (Exception e) {
            throwException(AppConstants.QUIZ_SUBMIT_VALIDATION_ERROR_CODE, AppConstants.QUIZ_SUBMIT_VALIDATION_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    public Page<QuizAnswerSubmit> findTopStudents(String quizName, String quizId, Integer noOfStudents, Pageable pageable){

         return quizAnswerSubmitRepository.findToppers(quizName,pageable);
    }

    public List<Quiz> getQuizListByStatusAndTenant(String status, String tenant) {
        return quizRepository.findByStatusAndTenant(status, tenant);
    }

    /*private void addQuestionNumber(List<QuestionDto> questions) {
        int index = 1;
        for(QuestionDto questionDto : questions){
            questionDto.setQuestionNumber(index);
            index++;
        }
    }

    private List<QuestionAndOptionResponseDto> getQuestionOptionDtoWithoutAnswer(List<QuestionDto> questions) {
        List<QuestionAndOptionResponseDto> questionAndOptionResponseDtos = new ArrayList<>();
        for(QuestionDto questionDto : questions) {
            QuestionAndOptionResponseDto questionAndOptionResponseDto = new QuestionAndOptionResponseDto();
            questionAndOptionResponseDto.setQuestionNumber(questionDto.getQuestionNumber());
            questionAndOptionResponseDto.setQuestionStatement(questionDto.getQuestionStatement());
            //questionAndOptionResponseDto.setQuestionWeightage(questionDto.getQuestionWeightage());
           // questionAndOptionResponseDto.setOptions(questionDto.getOptions());
            questionAndOptionResponseDtos.add(questionAndOptionResponseDto);
        }
        return questionAndOptionResponseDtos;

    }*/
}







