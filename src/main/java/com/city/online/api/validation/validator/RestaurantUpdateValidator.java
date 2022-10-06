package com.city.online.api.validation.validator;

import com.city.online.api.constant.AppConstants;
import com.city.online.api.exception.BusinessException;
import com.city.online.api.model.RestaurantUser;
import com.city.online.api.model.pojo.AccountDetails;
import com.city.online.api.model.pojo.Restaurant;
import com.city.online.api.repository.RestaurantUserRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class RestaurantUpdateValidator implements Validator {
    @Autowired
    RestaurantUserRespository restaurantUserRespository;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object requestObject, Errors errors) {
        RestaurantUser restaurantOwner = (RestaurantUser) requestObject;
        Optional<RestaurantUser> restaurantOwnerUpdated = restaurantUserRespository.findById(restaurantOwner.getId());
        restaurantOwner = restaurantOwnerUpdated.get();
        // check whether valid 'id' is entered
        if(!restaurantOwnerUpdated.isPresent()) {
            throwException(AppConstants.RECORDS_NOT_FOUND_ERROR_CODE,AppConstants.RECORDS_NOT_FOUND_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }

        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" +"[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        // check if required fields are missing
        if (restaurantOwner.getUsername().isEmpty() || restaurantOwner.getOwnerMobileNumber().toString().isEmpty() ||
                restaurantOwner.getOwnerEmailId().isEmpty() || isAccountDetailMissing(restaurantOwner.getAccountDetails())
                || isRestaurantDetailMissing(restaurantOwner.getRestaurantDetails()))
        {
            throwException(AppConstants.MISSING_REQUIRED_FIELD_ERROR_CODE,AppConstants.MISSING_REQUIRED_FIELD_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }

        // check if the mobile number contains 10 digits (not other characters)
        if(!String.valueOf(restaurantOwner.getOwnerMobileNumber()).matches("^\\d{10}$")){
            throwException(AppConstants.MOBILE_NUMBER_INVALID_ERROR_CODE,AppConstants.MOBILE_NUMBER_INVALID_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }
        if(Objects.nonNull(restaurantOwner.getManagerMobileNumber()) && restaurantOwner.getManagerMobileNumber()!=0){
            if(!String.valueOf(restaurantOwner.getManagerMobileNumber()).matches("^\\d{10}$")){
                throwException(AppConstants.MOBILE_NUMBER_INVALID_ERROR_CODE,AppConstants.MOBILE_NUMBER_INVALID_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
            }
        }

        // check if the email follows proper format
        if(!restaurantOwner.getOwnerEmailId().matches(regexPattern)) {
            throwException(AppConstants.EMAIL_FORMAT_ERROR_CODE,AppConstants.EMAIL_FORMAT_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }
        if(Objects.nonNull(restaurantOwner.getManagerEmailId()) && !restaurantOwner.getManagerEmailId().isEmpty()){
            if(!restaurantOwner.getManagerEmailId().matches(regexPattern)){
                throwException(AppConstants.EMAIL_FORMAT_ERROR_CODE,AppConstants.EMAIL_FORMAT_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
            }
        }

        // pincode validation
        if(!String.valueOf(restaurantOwner.getRestaurantDetails().getPinCode()).matches("^\\d{6}$")){
            throwException(AppConstants.PINCODE_ERROR_CODE,AppConstants.PINCODE_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }

        // pancard validation
       /* if(!restaurantOwner.getAccountDetails().getPanNumber().matches("^[A-Z0-9]{10}$")) {
            throwException(AppConstants.PAN_NUMBER_ERROR_CODE,AppConstants.PAN_NUMBER_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }*/

        /*// photo url validation
        if(!isValidURL(restaurantOwner.getRestaurantDetails().getRestaurantImageUrls())){
            throwException(AppConstants.INVALID_URL_ERROR_CODE,AppConstants.INVALID_URL_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }*/

       /* // check whether operating days entered are valid
        if(!isValidDay(restaurantOwner.getRestaurantDetails().getOperatingDays())){
            throwException(AppConstants.INVALID_WEEKDAYS_ERROR_CODE,AppConstants.INVALID_WEEKDAYS_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }*/
    }
    private boolean isRestaurantDetailMissing(Restaurant restaurantDetails) {
        if(restaurantDetails.getRestaurantName().isEmpty() || restaurantDetails.getCityName().isEmpty()
                || restaurantDetails.getPinCode().toString().isEmpty())

            return true;
        return false;
    }

    private boolean isAccountDetailMissing(AccountDetails accountDetails) {
        if(accountDetails.getBankName().isEmpty() || accountDetails.getBankIfscCode().isEmpty())
            return true;
        return false;
    }

    private boolean isValidURL(List<String> imageUrls){
        try{
            for(String url:imageUrls){
                new URL(url).toURI();
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private boolean isValidDay(List<String> operatingDays){
        List<String> weekDays = new ArrayList<>();
        weekDays.add("MONDAY");
        weekDays.add("TUESDAY");
        weekDays.add("WEDNESDAY");
        weekDays.add("THURSDAY");
        weekDays.add("FRIDAY");
        weekDays.add("SATURDAY");
        weekDays.add("SUNDAY");

        for (String day:operatingDays){
            if(!weekDays.contains(day.toUpperCase(Locale.ROOT)))
                return false;
        }
        return true;
    }

    private void throwException(String code, String errorMessage, HttpStatus httpStatus) {
        throw new BusinessException(code, errorMessage ,httpStatus , null);
    }
}
