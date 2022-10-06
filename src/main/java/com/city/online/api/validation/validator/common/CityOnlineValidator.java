package com.city.online.api.validation.validator.common;

import com.city.online.api.validation.CityOnlineValidatorFactoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Slf4j
public class CityOnlineValidator {

    @Autowired
    CityOnlineValidatorFactoryImpl cityOnlineValidatorFactory;

    public void validate(Object requestObject, String validationId, Errors errors) {
        log.info("In method validate. Arguments : validationRequestId : {} ", validationId);
        Validator cityOnlineValidator = cityOnlineValidatorFactory.getValidator(validationId);
        cityOnlineValidator.validate(requestObject, errors);
    }

}
