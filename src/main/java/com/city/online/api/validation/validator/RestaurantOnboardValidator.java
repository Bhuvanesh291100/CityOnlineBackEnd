package com.city.online.api.validation.validator;

import com.city.online.api.constant.AppConstants;
import com.city.online.api.dto.request.RestaurantOnboardRequestDto;
import com.city.online.api.exception.BusinessException;
import com.city.online.api.model.User;
import com.city.online.api.model.pojo.AccountDetails;
import com.city.online.api.model.pojo.Restaurant;
import com.city.online.api.repository.RestaurantUserRespository;
import com.city.online.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
public class RestaurantOnboardValidator implements Validator {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RestaurantUserRespository restaurantUserRespository;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object requestObject, Errors errors){
        RestaurantOnboardRequestDto restaurantOnboardRequestDto = (RestaurantOnboardRequestDto) requestObject;
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" +"[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        // check if required fields are missing
        if(StringUtils.isEmpty(restaurantOnboardRequestDto.getUsername()) || StringUtils.isEmpty(restaurantOnboardRequestDto.getOwnerMobileNumber().toString()) ||
                StringUtils.isEmpty(restaurantOnboardRequestDto.getOwnerEmailId()) ||
                StringUtils.isEmpty(restaurantOnboardRequestDto.getFirstName()) || StringUtils.isEmpty(restaurantOnboardRequestDto.getLastName()) ||
                isAccountDetailMissing(restaurantOnboardRequestDto.getAccountDetails()) || isRestaurantDetailMissing(restaurantOnboardRequestDto.getRestaurantDetails()))
        {
            throwException(AppConstants.MISSING_REQUIRED_FIELD_ERROR_CODE,AppConstants.MISSING_REQUIRED_FIELD_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }

        if(Objects.isNull(userRepository.findUserByUsername(restaurantOnboardRequestDto.getUsername()))) {
            throwException(AppConstants.USER_VALIDATION_ERROR_CODE,AppConstants.USER_VALIDATION_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }

        if(Objects.nonNull(restaurantUserRespository.findRestaurantUserByUsername(restaurantOnboardRequestDto.getUsername()))) {
            throwException(AppConstants.USER_DUPLICATION_ERROR_CODE,AppConstants.USER_DUPLICATION_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }
        // check if the mobile number contains 10 digits (not other characters)
        if(!String.valueOf(restaurantOnboardRequestDto.getOwnerMobileNumber()).matches("^\\d{10}$")){
            throwException(AppConstants.MOBILE_NUMBER_INVALID_ERROR_CODE,AppConstants.MOBILE_NUMBER_INVALID_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }
        if(Objects.nonNull(restaurantOnboardRequestDto.getManagerMobileNumber())){
            if(!String.valueOf(restaurantOnboardRequestDto.getManagerMobileNumber()).matches("^\\d{10}$")){
                throwException(AppConstants.MOBILE_NUMBER_INVALID_ERROR_CODE,AppConstants.MOBILE_NUMBER_INVALID_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
            }
        }

        // check if the email follows proper format
        if(!restaurantOnboardRequestDto.getOwnerEmailId().matches(regexPattern)){
            throwException(AppConstants.EMAIL_FORMAT_ERROR_CODE,AppConstants.EMAIL_FORMAT_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }
        if(!StringUtils.isEmpty(restaurantOnboardRequestDto.getManagerEmailId())){
            if(!restaurantOnboardRequestDto.getManagerEmailId().matches(regexPattern)){
                throwException(AppConstants.EMAIL_FORMAT_ERROR_CODE,AppConstants.EMAIL_FORMAT_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
            }
        }

        // pincode validation
        if(!String.valueOf(restaurantOnboardRequestDto.getRestaurantDetails().getPinCode()).matches("^\\d{6}$")){
            throwException(AppConstants.PINCODE_ERROR_CODE,AppConstants.PINCODE_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }

       /* // pancard validation
        if(!restaurantOnboardRequestDto.getAccountDetails().getPanNumber().matches("^[A-Z0-9]{10}$")){
            throwException(AppConstants.PAN_NUMBER_ERROR_CODE,AppConstants.PAN_NUMBER_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }
*/
        // photo url validation
        /*if(!isValidURL(restaurantOnboardRequestDto.getRestaurantDetails().getRestaurantImageUrls())){
            throwException(AppConstants.INVALID_URL_ERROR_CODE,AppConstants.INVALID_URL_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }*/

        // check whether operating days entered are valid
        /*if(!isValidDay(restaurantOnboardRequestDto.getRestaurantDetails().getOperatingDays())){
            throwException(AppConstants.INVALID_WEEKDAYS_ERROR_CODE,AppConstants.INVALID_WEEKDAYS_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }*/
    }

    private boolean isRestaurantDetailMissing(Restaurant restaurantDetails) {
        if(restaurantDetails.getRestaurantName().isEmpty() || restaurantDetails.getCityName().isEmpty() || restaurantDetails.getPinCode().toString().isEmpty())
            return true;
        return false;
    }

    private boolean isAccountDetailMissing(AccountDetails accountDetails) {
        if(StringUtils.isEmpty(accountDetails.getBankName()) || StringUtils.isEmpty(accountDetails.getBankIfscCode()))
            return true;

        return !StringUtils.isNumeric(accountDetails.getBankAccountNumber());
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
