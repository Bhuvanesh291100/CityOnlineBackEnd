package com.city.online.api.validation.validator;

import com.city.online.api.constant.AppConstants;
import com.city.online.api.dto.request.QuizUserOnboardRequestDto;
import com.city.online.api.exception.BusinessException;
import com.city.online.api.model.User;
import com.city.online.api.repository.QuizUserRepository;
import com.city.online.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class QuizUserOnboardValidator implements Validator{
    @Autowired
    UserRepository userRepository;

    @Autowired
    QuizUserRepository quizUserRepository;

    @Override
    public boolean supports(@NotNull Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(@NotNull Object requestObject, @NotNull Errors errors) {

        QuizUserOnboardRequestDto quizUserOnboardRequestDto = (QuizUserOnboardRequestDto) requestObject;

        Optional<User> user = userRepository.findByUsername(quizUserOnboardRequestDto.getUsername());

        checkRequiredFieldsAreMissing(quizUserOnboardRequestDto);

        // check if user is signed up already
        /*if(!user.isPresent()){
            throwException(AppConstants.USER_VALIDATION_ERROR_CODE, AppConstants.USER_VALIDATION_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }*/

//        // check if email is already registered or not
//        if (Objects.nonNull(quizUserOnboardRequestDto.getEmailId()) && Boolean.TRUE.equals(quizUserRepository.existsByEmailId(quizUserOnboardRequestDto.getEmailId()))) {
//            throwException(AppConstants.EMAIL_DUPLICATION_ERROR_CODE,AppConstants.EMAIL_DUPLICATION_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
//        }

        // check if the email follows proper format
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if(StringUtils.isNotEmpty(quizUserOnboardRequestDto.getEmailId()) && !quizUserOnboardRequestDto.getEmailId().matches(regexPattern)){
            throwException(AppConstants.EMAIL_FORMAT_ERROR_CODE,AppConstants.EMAIL_FORMAT_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }

        // check if the mobile number contains 10 digits (not other characters)
        if(Objects.nonNull(quizUserOnboardRequestDto.getParentMobileNumber()) && !String.valueOf(quizUserOnboardRequestDto.getParentMobileNumber()).matches("^\\d{10}$")){
            throwException(AppConstants.MOBILE_NUMBER_INVALID_ERROR_CODE,AppConstants.MOBILE_NUMBER_INVALID_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }
        if(!String.valueOf(quizUserOnboardRequestDto.getMobileNumber()).matches("^\\d{10}$")){
            throwException(AppConstants.MOBILE_NUMBER_INVALID_ERROR_CODE,AppConstants.MOBILE_NUMBER_INVALID_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }
    }

    private void checkRequiredFieldsAreMissing(QuizUserOnboardRequestDto quizUserOnboardRequestDto){
        if(quizUserOnboardRequestDto.getUsername().isEmpty() ||  quizUserOnboardRequestDto.getFirstName().isEmpty()
                || quizUserOnboardRequestDto.getLastName().isEmpty()
        || null == quizUserOnboardRequestDto.getMobileNumber() || null == quizUserOnboardRequestDto.getPinCode()
        || quizUserOnboardRequestDto.getQualification().toString().isEmpty()) {
            throwException(AppConstants.MISSING_REQUIRED_FIELD_ERROR_CODE, AppConstants.MISSING_REQUIRED_FIELD_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }
    }

    private void throwException(String code, String errorMessage, HttpStatus httpStatus) {
        throw new BusinessException(code, errorMessage , HttpStatus.BAD_REQUEST, null);
    }
}