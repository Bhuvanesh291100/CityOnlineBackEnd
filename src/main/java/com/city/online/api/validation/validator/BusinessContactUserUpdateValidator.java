package com.city.online.api.validation.validator;

import com.city.online.api.constant.AppConstants;
import com.city.online.api.exception.BusinessException;
import com.city.online.api.model.BusinessCategory;
import com.city.online.api.model.BusinessContactUser;
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
public class BusinessContactUserUpdateValidator implements Validator{
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

        BusinessContactUser businessContactUser = (BusinessContactUser) requestObject;
        Optional<BusinessContactUser> businessContactUserUpdated = businessContactUserRepository.findById(businessContactUser.getId());
        businessContactUser = businessContactUserUpdated.get();

        // check whether business user is fetched from database
        if(!businessContactUserUpdated.isPresent()){
            throwException(AppConstants.RECORDS_NOT_FOUND_ERROR_CODE, AppConstants.RECORDS_NOT_FOUND_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }

        String mainBusinessCategory = businessContactUser.getMainBusinessCategory();
        List<String> subBusinessCategoryList = businessContactUser.getSubBusinessCategoryList();

        Optional<User> user = userRepository.findByUsername(businessContactUser.getUsername());

        // check whether all required fields are present
        if(businessContactUser.getUsername().isEmpty() || businessContactUser.getFirstName().isEmpty() ||
                businessContactUser.getLastName().isEmpty() ||
                businessContactUser.getMobileNumber1().toString().isEmpty() || businessContactUser.getBusinessName().isEmpty() ||
                businessContactUser.getMainBusinessCategory().isEmpty())
        {
            throwException(AppConstants.MISSING_REQUIRED_FIELD_ERROR_CODE, AppConstants.MISSING_REQUIRED_FIELD_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        // check if the email follows proper format
        if(Objects.nonNull(businessContactUser.getEmailId()) && !businessContactUser.getEmailId().isEmpty() && !businessContactUser.getEmailId().matches(regexPattern)){
            throwException(AppConstants.EMAIL_FORMAT_ERROR_CODE,AppConstants.EMAIL_FORMAT_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }

        // check if the mobile number contains 10 digits (not other characters)
        if(!String.valueOf(businessContactUser.getMobileNumber1()).matches("^\\d{10}$")){
            throwException(AppConstants.MOBILE_NUMBER_INVALID_ERROR_CODE,AppConstants.MOBILE_NUMBER_INVALID_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }

       /* if(!String.valueOf(businessContactUser.getMobileNumber2()).matches("^\\d{10}$")){
            throwException(AppConstants.MOBILE_NUMBER_INVALID_ERROR_CODE,AppConstants.MOBILE_NUMBER_INVALID_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }*/

      /*  if(!businessContactUser.getSubBusinessCategoryList().isEmpty()){
            // check whether user selected sub-categories are valid or not
            if (!isValidSubCategories(mainBusinessCategory,subBusinessCategoryList)){
                throwException(AppConstants.BUSINESS_SUB_CATEGORY_ERROR_CODE, AppConstants.BUSINESS_SUB_CATEGORY_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
            }
        }*/
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
