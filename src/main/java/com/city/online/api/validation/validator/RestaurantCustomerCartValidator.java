package com.city.online.api.validation.validator;

import com.city.online.api.constant.AppConstants;
import com.city.online.api.dto.request.AddToCartRequestDto;
import com.city.online.api.exception.BusinessException;
import com.city.online.api.model.RestaurantUser;
import com.city.online.api.model.User;
import com.city.online.api.model.Product;
import com.city.online.api.repository.RestaurantUserRespository;
import com.city.online.api.repository.UserRepository;
import com.city.online.api.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class RestaurantCustomerCartValidator implements Validator {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RestaurantService restaurantService;

    @Autowired
    RestaurantUserRespository restaurantUserRespository;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object requestObject, Errors errors) {
        AddToCartRequestDto addToCartRequestDto = (AddToCartRequestDto) requestObject;

        Optional<User> user = userRepository.findByUsername(addToCartRequestDto.getUsername());
        //Optional<RestaurantUser> restaurantUser = restaurantUserRespository.findById(addToCartRequestDto.getRestaurantUserId());

        // check if user is valid
        if(!user.isPresent()){
            throwException(AppConstants.USER_VALIDATION_ERROR_CODE, AppConstants.USER_VALIDATION_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        /*if(!restaurantUser.isPresent()){
            throwException(AppConstants.USER_VALIDATION_ERROR_CODE, AppConstants.USER_VALIDATION_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }*/

        // check if any required field is missing
        if(addToCartRequestDto.getUsername().isEmpty()  || isOrderItemMissing(addToCartRequestDto.getCartItem())){
            throwException(AppConstants.MISSING_REQUIRED_FIELD_ERROR_CODE,AppConstants.MISSING_REQUIRED_FIELD_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }

        // check whether restaurantId is valid or not
       /* if(!isRestaurantIdValid(addToCartRequestDto.getRestaurantUserId())){
            throwException(AppConstants.RESTAURANT_DOEST_EXIST_ERROR_CODE, AppConstants.RESTAURANT_DOEST_EXIST_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }*/
    }

    private boolean isRestaurantIdValid(String restaurantId) {
        Optional<RestaurantUser> restaurantUser = restaurantUserRespository.findById(restaurantId);
        if(restaurantUser.isPresent())
            return true;
        return false;
    }

    //TODO - get the quantity from the order DTO
    private boolean isOrderItemMissing(Product orderItem) {
            //TODO - use get ordered count
            if(orderItem.getItemName().isEmpty()) {
                return true;
            }
        return false;
    }

    private void throwException(String code, String errorMessage, HttpStatus httpStatus) {
        throw new BusinessException(code, errorMessage ,httpStatus , null);
    }
}
