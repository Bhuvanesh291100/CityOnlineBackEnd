package com.city.online.api.exception.common;

import com.city.online.api.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Component
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value =  BusinessException.class)
    @ResponseBody
    public ResponseEntity<Object> handleBusinessException(BusinessException exception) {
        return new ResponseEntity<>(getAppErrorObject(exception),exception.getHttpStatus());
    }

    private ErrorDto getAppErrorObject(BusinessException exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode(exception.getCode());
        errorDto.setErrorMessage(exception.getErrorMessage());
        errorDto.setHttpStatusCode(exception.getHttpStatusCode());
        errorDto.setDescription("Validation Errors");

        return errorDto;
    }

}
