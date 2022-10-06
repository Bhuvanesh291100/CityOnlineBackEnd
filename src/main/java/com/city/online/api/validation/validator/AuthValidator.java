package com.city.online.api.validation.validator;

import com.city.online.api.constant.AppConstants;
import com.city.online.api.dto.request.SignUpRequest;
import com.city.online.api.exception.BusinessException;
import com.city.online.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import java.util.stream.Collectors;

@Component
@Slf4j
public class AuthValidator implements Validator {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object requestObject, Errors errors) {
        validateInput(errors);

        SignUpRequest signUpRequest = (SignUpRequest) requestObject;

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throwException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmailId(signUpRequest.getEmail())) {
            throwException("Error: Email is already in use!");
        }
    }

    private void validateInput(Errors errors) {
        if(errors.hasErrors()) {
                String errorString = getFormattedErrors(errors);
                log.error("AuthValidator error {} ", errorString);
                throwException(errorString);
        }
    }

    private String getFormattedErrors(Errors errors) {
        return errors.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));
    }

    private void throwException(String errorMessage) {
        throw new BusinessException(AppConstants.AUTH_SIGN_UP_VALIDATOR_ERROR_CODE, errorMessage , HttpStatus.BAD_REQUEST, null);
    }
}
