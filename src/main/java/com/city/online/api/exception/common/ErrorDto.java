package com.city.online.api.exception.common;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class ErrorDto implements Serializable {
    private String code;
    private String errorMessage;
    private Integer httpStatusCode;

    private String description;

    public void setHttpStatus(HttpStatus httpStatus) {
    }
}
