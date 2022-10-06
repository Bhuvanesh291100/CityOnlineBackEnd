package com.city.online.api.validation.validator;

import com.city.online.api.constant.AppConstants;
import com.city.online.api.dto.request.ProductOnboardDto;
import com.city.online.api.dto.request.RestaurantFoodMenuCreateRequestDto;
import com.city.online.api.exception.BusinessException;
import com.city.online.api.model.Product;
import com.city.online.api.repository.RestaurantUserRespository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.net.URL;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class RestaurantFoodMenuValidator implements Validator {

    @Autowired
    RestaurantUserRespository restaurantUserRespository;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object requestObject, Errors errors){
        RestaurantFoodMenuCreateRequestDto restaurantFoodMenuCreateRequestDto = (RestaurantFoodMenuCreateRequestDto) requestObject;
        String restaurantUserId = restaurantFoodMenuCreateRequestDto.getFoodItems().get(0).getVendorId();

        /*if(!restaurantUserId.equals(restaurantFoodMenuCreateRequestDto.getRestaurantUserId())) {
            throwException(AppConstants.RESTAURANT_USER_VALIDATION_ERROR_CODE,AppConstants.RESTAURANT_USER_VALIDATION_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }*/

       /* if(!restaurantUserRespository.findById(restaurantFoodMenuCreateRequestDto.getRestaurantUserId()).isPresent()) {
            throwException(AppConstants.RESTAURANT_USER_VALIDATION_ERROR_CODE,AppConstants.RESTAURANT_USER_VALIDATION_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }*/
        // check if any required field is missing
        if(Objects.isNull(restaurantFoodMenuCreateRequestDto.getFoodItems())) {
            throwException(AppConstants.MISSING_REQUIRED_FIELD_ERROR_CODE,AppConstants.MISSING_REQUIRED_FIELD_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }

        // check whether all of the required food item details are not null or empty
        if(!isValidItemDetails(restaurantFoodMenuCreateRequestDto.getFoodItems())) {
            throwException(AppConstants.MISSING_REQUIRED_FIELD_ERROR_CODE,AppConstants.MISSING_REQUIRED_FIELD_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }


        // photo url validation
        if(!isValidURL(restaurantFoodMenuCreateRequestDto.getFoodItems())){
            throwException(AppConstants.INVALID_URL_ERROR_CODE,AppConstants.INVALID_URL_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isValidItemDetails(List<ProductOnboardDto> productList) {

        for (ProductOnboardDto foodItem : productList) {
            if (StringUtils.isEmpty(foodItem.getItemName()) || StringUtils.isEmpty(foodItem.getDescription())
                    || Objects.isNull(foodItem.getInventoryCount()) || Objects.isNull(foodItem.getFoodImageUrls())
                    || foodItem.getFoodImageUrls().isEmpty() || Objects.isNull(foodItem.getItemPrice())
                    || StringUtils.isEmpty(foodItem.getUsername())) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidURL(List<ProductOnboardDto> foodItemList){

        try{
            for(ProductOnboardDto dish: foodItemList) {
                List<String> imageUrls = dish.getFoodImageUrls();
                for (String url : imageUrls) {
                    new URL(url).toURI();
                }
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private void throwException(String code, String errorMessage, HttpStatus httpStatus) {
        throw new BusinessException(code, errorMessage ,httpStatus , null);
    }
}
