package com.city.online.api.validation.validator;

import com.city.online.api.constant.AppConstants;
import com.city.online.api.dto.request.OrderStatusUpdateRequestDto;
import com.city.online.api.exception.BusinessException;
import com.city.online.api.model.UserOrder;
import com.city.online.api.repository.CheckoutRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@Slf4j
public class UpdateOrderStatusValidator implements Validator {

    @Autowired
    CheckoutRepository checkoutRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object requestObject, Errors errors) {
        OrderStatusUpdateRequestDto orderStatusUpdateRequestDto = (OrderStatusUpdateRequestDto) requestObject;

        Optional<UserOrder> checkout = checkoutRepository.findById(orderStatusUpdateRequestDto.getId());

        // if 'id' entered is valid
        if(checkout.isPresent()){
            UserOrder checkoutOrder1 = checkout.get();

            // check whether checkoutId is valid or not
            if(!checkoutOrder1.getId().equals(orderStatusUpdateRequestDto.getCheckoutId())){
                throwException(AppConstants.INVALID_CHECKOUT_ID_ERROR_CODE,AppConstants.INVALID_CHECKOUT_ID__ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
            }



        }else{
            throwException(AppConstants.INVALID_ID_ERROR_CODE,AppConstants.INVALID_ID__ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }
    }
    private void throwException(String code, String errorMessage, HttpStatus httpStatus) {
        throw new BusinessException(code, errorMessage ,httpStatus , null);
    }
}
