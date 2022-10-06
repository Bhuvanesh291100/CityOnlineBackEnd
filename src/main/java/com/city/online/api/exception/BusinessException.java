package com.city.online.api.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BusinessException extends RuntimeException {

    private String code;
    private String errorMessage;
    private Integer httpStatusCode;
    private HttpStatus httpStatus;

    //http status
    public BusinessException(String code, String errorMessage, HttpStatus httpStatus,Throwable cause) {
        super(errorMessage, cause);
        this.code = code;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
    //http statusCode
    public BusinessException(String code, String errorMessage, Integer httpStatusCode, Throwable cause) {
        super(errorMessage, cause);
        this.code = code;
        this.errorMessage = errorMessage;
        this.httpStatusCode = httpStatusCode;
    }
}
