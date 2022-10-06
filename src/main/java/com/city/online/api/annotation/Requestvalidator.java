package com.city.online.api.annotation;

import com.city.online.api.dto.request.UserCreateRequestDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Requestvalidator implements ConstraintValidator<CheckRequestDto, UserCreateRequestDto> {
    String message;

    Class<?> sourceClass;


    @Override
    public void initialize(CheckRequestDto constraintAnnotation) {
            this.message = constraintAnnotation.message();
            this.sourceClass = constraintAnnotation.sourceClass();
    }

    @Override
    public boolean isValid(UserCreateRequestDto userCreateRequestDto, ConstraintValidatorContext constraintValidatorContext) {
        if(Objects.nonNull(userCreateRequestDto)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            List<String> errors = new ArrayList<>();

            validateMessage(userCreateRequestDto.getUsername(), errors, constraintValidatorContext);
        }
        return false;
    }

    private void validateMessage(String username, List<String> errors, ConstraintValidatorContext constraintValidatorContext) {
        if(null == username) {
            errors.add("Null username");
            constraintValidatorContext.buildConstraintViolationWithTemplate("Null Username").addPropertyNode("node")
                    .inIterable().atIndex(1).addConstraintViolation();
        }
    }
}
