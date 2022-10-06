package com.city.online.api.validation.validator;

import com.city.online.api.constant.AppConstants;
import com.city.online.api.dto.request.BusinessContactUserOnboardRequestDto;
import com.city.online.api.exception.BusinessException;
import com.city.online.api.model.BusinessCategory;
import com.city.online.api.model.User;
import com.city.online.api.model.pojo.Category;
import com.city.online.api.repository.BusinessCategoryRepository;
import com.city.online.api.repository.BusinessContactUserRepository;
import com.city.online.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class BusinessContactUserOnboardValidator implements Validator{
    @Autowired
    UserRepository userRepository;

    @Autowired
    BusinessCategoryRepository businessCategoryRepository;

    @Autowired
    BusinessContactUserRepository businessContactUserRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object requestObject, Errors errors) {

        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        BusinessContactUserOnboardRequestDto businessContactUserOnboardRequestDto = (BusinessContactUserOnboardRequestDto) requestObject;
        String mainBusinessCategory = businessContactUserOnboardRequestDto.getMainBusinessCategory();
        List<String> subBusinessCategoryList = businessContactUserOnboardRequestDto.getSubBusinessCategoryList();

        Optional<User> user = userRepository.findByUsername(businessContactUserOnboardRequestDto.getUsername());

        // check whether all required fields are present
        if(businessContactUserOnboardRequestDto.getUsername().isEmpty() || businessContactUserOnboardRequestDto.getFirstName().isEmpty() ||
                businessContactUserOnboardRequestDto.getLastName().isEmpty() ||
                businessContactUserOnboardRequestDto.getMobileNumber1().toString().isEmpty() || businessContactUserOnboardRequestDto.getBusinessName().isEmpty()
                || businessContactUserOnboardRequestDto.getMainBusinessCategory().isEmpty())
        {
            throwException(AppConstants.MISSING_REQUIRED_FIELD_ERROR_CODE, AppConstants.MISSING_REQUIRED_FIELD_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        // check if user is signed up already
        if(!user.isPresent()){
            throwException(AppConstants.USER_VALIDATION_ERROR_CODE, AppConstants.USER_VALIDATION_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }

       /* // check if email is already registered or not
        if (businessContactUserRepository.existsByEmailId(businessContactUserOnboardRequestDto.getEmailId())) {
            throwException(AppConstants.EMAIL_DUPLICATION_ERROR_CODE,AppConstants.EMAIL_DUPLICATION_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }*/

       /* // check if the email follows proper format
        if(!businessContactUserOnboardRequestDto.getEmailId().matches(regexPattern)){
            throwException(AppConstants.EMAIL_FORMAT_ERROR_CODE,AppConstants.EMAIL_FORMAT_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }*/

        // check if the mobile number contains 10 digits (not other characters)
        if(!String.valueOf(businessContactUserOnboardRequestDto.getMobileNumber1()).matches("^\\d{10}$")){
            throwException(AppConstants.MOBILE_NUMBER_INVALID_ERROR_CODE,AppConstants.MOBILE_NUMBER_INVALID_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        if(Objects.nonNull(businessContactUserOnboardRequestDto.getMobileNumber2()) && !String.valueOf(businessContactUserOnboardRequestDto.getMobileNumber2()).matches("^\\d{10}$")){
            throwException(AppConstants.MOBILE_NUMBER_INVALID_ERROR_CODE,AppConstants.MOBILE_NUMBER_INVALID_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        if(Objects.nonNull(businessContactUserOnboardRequestDto.getSubBusinessCategoryList()) && !businessContactUserOnboardRequestDto.getSubBusinessCategoryList().isEmpty()){
            // check whether user selected sub-categories are valid or not
            if (!isValidSubCategories(mainBusinessCategory,subBusinessCategoryList)){
                throwException(AppConstants.BUSINESS_SUB_CATEGORY_ERROR_CODE, AppConstants.BUSINESS_SUB_CATEGORY_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
            }
        }
    }

    private boolean isValidSubCategories(String mainBusinessCategory,List<String> subBusinessCategoryList) {
        List<String> subCategoryList = getSubCategoryList(mainBusinessCategory);
        for(String subCat:subBusinessCategoryList){
            if(!subCategoryList.contains(subCat))
                return false;
        }
        return true;
    }
    private List<String> getSubCategoryList(String mainCategory){
        BusinessCategory businessCategory = businessCategoryRepository.getAllSubCategoriesByMainCategory(mainCategory);
        List<String> respectiveSubCategories = new ArrayList<>();
        for(Category subCat : businessCategory.getSubCategoryList()){
            respectiveSubCategories.add(subCat.getCategoryCode());
        }
        return respectiveSubCategories;
    }

    private void throwException(String code, String errorMessage, HttpStatus httpStatus) {
        throw new BusinessException(code, errorMessage ,httpStatus , null);
    }
}
