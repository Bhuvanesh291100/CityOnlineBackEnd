package com.city.online.api.controller;


import com.city.online.api.constant.AppConstants;
import com.city.online.api.dto.request.QuizAnswerSubmitDto;
import com.city.online.api.dto.request.QuizRequestDto;
import com.city.online.api.dto.request.QuizUserOnboardRequestDto;
import com.city.online.api.model.Quiz;
import com.city.online.api.model.QuizAnswerSubmit;
import com.city.online.api.model.QuizUserRegistration;
import com.city.online.api.repository.QuizRepository;
import com.city.online.api.service.QuizService;
import com.city.online.api.service.QuizUserService;
import com.city.online.api.validation.validator.common.CityOnlineValidator;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    QuizService quizService;

    @Autowired
    QuizUserService quizUserService;

    @Autowired
    CityOnlineValidator cityOnlineValidator;

    QuizRepository quizRepository;

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Quiz> createQuiz(@NotNull @Valid @RequestBody QuizRequestDto quizRequestDto, Errors errors) {
        //QuizValidator
        cityOnlineValidator.validate(quizRequestDto, "QUIZ_VALIDATOR", errors);
        Quiz quizUser = quizService.createQuiz(quizRequestDto);
        return new ResponseEntity<>(quizUser, HttpStatus.CREATED);
    }

    @GetMapping("/getQuizById")
    public ResponseEntity<Quiz> getQuizById(@RequestParam String qId) {
        Quiz storeQuiz = quizService.getQuizById(qId);
        return new ResponseEntity<>(storeQuiz, HttpStatus.OK);
    }


    @GetMapping("/getByName")
    public ResponseEntity<Quiz> getQuizByName(@RequestParam String qName){
         Quiz storeQuiz = quizService.getByQuizName(qName);
        return new ResponseEntity<>(storeQuiz, HttpStatus.OK);
    }

    @GetMapping("/getByDate")
    public ResponseEntity<Quiz> getByQuizDate(@RequestParam String qDate) {
        Quiz storeQuiz = quizService.getByQuizDate(qDate);
        return new ResponseEntity<>(storeQuiz, HttpStatus.OK);
    }

    @PostMapping("/answer")
    public ResponseEntity<QuizAnswerSubmit> createQuiz(@NotNull @Valid @RequestBody QuizAnswerSubmitDto quizAnswerSubmitDto, Errors errors) {
        //QuizAnswerSubmitValidator
        cityOnlineValidator.validate(quizAnswerSubmitDto, "QUIZ_ANSWER_SUBMIT_VALIDATOR", errors);
        QuizAnswerSubmit quizAnswerSubmit = quizService.saveQuizAnswerSubmit(quizAnswerSubmitDto);
        return new ResponseEntity<>(quizAnswerSubmit, HttpStatus.OK);
    }

    @GetMapping("/getQuizTopper")
    public ResponseEntity<Page<QuizAnswerSubmit>> getByQuizTopper(@RequestParam String quizName, @RequestParam String quizId,@RequestParam Integer noOfStudents,@RequestParam Integer page, @RequestParam Integer size) {
        List<Sort.Order> sortList = new ArrayList<>();
        sortList.add(new Sort.Order(Sort.Direction.DESC, "score"));
        sortList.add(new Sort.Order(Sort.Direction.ASC, "timeTakenToSubmit"));
        Sort sort = Sort.by(sortList);
        Pageable pageable = PageRequest.of(page, size,sort);
        Page<QuizAnswerSubmit> storeQuiz = quizService.findTopStudents(quizName,quizId,noOfStudents,pageable);
        return new ResponseEntity<>(storeQuiz, HttpStatus.OK);
    }


    @PostMapping("/user/onboard")
    public ResponseEntity<QuizUserRegistration> onboardUser(@Valid @RequestBody QuizUserOnboardRequestDto quizUserOnboardRequestDto, Errors errors) {
        //log.info("Inside Quiz user onboard API");
        //QuizUserOnboardValidator
        cityOnlineValidator.validate(quizUserOnboardRequestDto, AppConstants.QUIZ_USER_ONBOARD_VALIDATOR, errors);
        QuizUserRegistration quizUserRegistration = quizUserService.createUser(quizUserOnboardRequestDto);
        return new ResponseEntity<>(quizUserRegistration, HttpStatus.CREATED);
    }

    @GetMapping("/status/list")
    public ResponseEntity<List<Quiz>> getQuizListByStatusAndTenant(@RequestParam(name = "status") String status, @RequestParam(name = "tenant") String tenant){
        List<Quiz> quizList = quizService.getQuizListByStatusAndTenant(status, tenant);
        return new ResponseEntity<>(quizList, HttpStatus.OK);
    }

    @GetMapping("/answer/username")
    public ResponseEntity<List<QuizAnswerSubmit>> getAllQuizTakenByUsername(@RequestParam(name = "username") String username) {
        List<QuizAnswerSubmit> quizAnswerSubmitList = quizUserService.findQuizTakenByUsername(username);
        if(Objects.nonNull(quizAnswerSubmitList) && !quizAnswerSubmitList.isEmpty()) {
            return new ResponseEntity<>(quizAnswerSubmitList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
