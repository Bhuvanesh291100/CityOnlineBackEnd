package com.city.online.api.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.CONSTRUCTOR})
@Constraint(validatedBy = Requestvalidator.class)
public @interface CheckRequestDto {

    String message() default "Default";
    
    Class<?> sourceClass();

    Class<? extends Payload>[] payload() default {};
}
