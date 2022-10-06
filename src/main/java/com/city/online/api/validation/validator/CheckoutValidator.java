package com.city.online.api.validation.validator;

import com.city.online.api.constant.AppConstants;
import com.city.online.api.dto.request.CheckoutRequestDto;
import com.city.online.api.exception.BusinessException;
import com.city.online.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
@Slf4j
public class CheckoutValidator implements Validator {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object requestObject, Errors errors) {
        CheckoutRequestDto checkoutRequestDto = (CheckoutRequestDto) requestObject;

        if(!userRepository.existsByUsername(checkoutRequestDto.getUsername())){
            throwException(AppConstants.USER_VALIDATION_ERROR_CODE,AppConstants.USER_VALIDATION_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }

        if(Objects.isNull(checkoutRequestDto.getDeliveryAddress()) || checkoutRequestDto.getDeliveryAddress().isEmpty()) {
            throwException(AppConstants.CHECKOUT_ADDRESS_VALIDATION_ERROR_CODE,AppConstants.CHECKOUT_ADDRESS_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }

        if(Objects.isNull(checkoutRequestDto.getPaymentMode()) || checkoutRequestDto.getPaymentMode().isEmpty()) {
            throwException(AppConstants.CHECKOUT_ADDRESS_VALIDATION_ERROR_CODE,AppConstants.CHECKOUT_ADDRESS_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }
    }
    private void throwException(String code, String errorMessage, HttpStatus httpStatus) {
        throw new BusinessException(code, errorMessage ,httpStatus , null);
    }
}
