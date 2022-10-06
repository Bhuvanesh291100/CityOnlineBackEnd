package com.city.online.api.exception.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AppError {
    List<ErrorDto> errors = new ArrayList<>();
}
