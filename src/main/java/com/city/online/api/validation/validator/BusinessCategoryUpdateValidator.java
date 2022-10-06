package com.city.online.api.validation.validator;

import com.city.online.api.constant.AppConstants;
import com.city.online.api.exception.BusinessException;
import com.city.online.api.model.BusinessCategory;
import com.city.online.api.model.pojo.Category;
import com.city.online.api.repository.BusinessCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class BusinessCategoryUpdateValidator implements Validator {
    @Autowired
    BusinessCategoryRepository businessCategoryRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object requestObject, Errors errors){
        BusinessCategory businessCategory = (BusinessCategory) requestObject;
        Optional<BusinessCategory> businessCategoryUpdated = businessCategoryRepository.findById(businessCategory.getId());

        // check whether valid 'id' is entered
        if(!businessCategoryUpdated.isPresent()){
            throwException(AppConstants.RECORDS_NOT_FOUND_ERROR_CODE, AppConstants.RECORDS_NOT_FOUND_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }

        // user entered details
        Category mainCategory = businessCategory.getMainCategory();
        List<Category> subCategory = businessCategory.getSubCategoryList();

        // get list of all mainCategory codes from database
        List<String> allMainCategoryCodes = getAllMainCategoryCodes();

        // check whether mainCategory is present in database
        if(!isMainCategoryPresent(allMainCategoryCodes, mainCategory.getCategoryCode())){
            throwException(AppConstants.MAIN_BUSINESS_CATEGORY_DOESNT_EXISTS_ERROR_CODE,AppConstants.MAIN_BUSINESS_CATEGORY_DOESNT_EXISTS_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }

        // check whether subCategory is present in database
        if(!isSubCategoryPresent(subCategory,mainCategory.getCategoryCode())){
            throwException(AppConstants.SUB_BUSINESS_CATEGORY_DOESNT_EXISTS_ERROR_CODE,AppConstants.SUB_BUSINESS_CATEGORY_DOESNT_EXISTS_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }

        // check whether any of the mainCategory field is missing
        if(isMainCategoryFieldMissing(mainCategory)){
            throwException(AppConstants.MAIN_CATEGORY_FIELDS_MISSING_ERROR_CODE, AppConstants.MAIN_CATEGORY_FIELDS_MISSING_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }

        // check whether any of the subCategory field is missing
        if(isSubCategoryFieldMissing(subCategory)){
            throwException(AppConstants.SUB_CATEGORY_FIELDS_MISSING_ERROR_CODE, AppConstants.SUB_CATEGORY_FIELDS_MISSING_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }
    }

    private List<String> getAllMainCategoryCodes() {
        List<BusinessCategory> categoryData = businessCategoryRepository.findAll();

        List<String> mainCategoryCodeList = new ArrayList<>();

        for(BusinessCategory mainCat: categoryData)
        {
            mainCategoryCodeList.add(mainCat.getMainCategory().getCategoryCode());
        }
        return mainCategoryCodeList;
    }

    private boolean isMainCategoryPresent(List<String> allMainCategoryCodes, String mainCategory) {
        if(allMainCategoryCodes.contains(mainCategory))  return true;
        return false;
    }

    private boolean isSubCategoryPresent(List<Category> subCategory, String mainCategory) {
        List<BusinessCategory> businessCategory = businessCategoryRepository.findAll();
        if(!businessCategory.contains(subCategory))
            return false;
        return true;
    }

    private boolean isMainCategoryFieldMissing(Category mainCategory){
        return (mainCategory.getCategoryCode().isEmpty() || mainCategory.getCategoryName().isEmpty() || mainCategory.getCategoryDescription().isEmpty())?true:false;
    }

    private boolean isSubCategoryFieldMissing(List<Category> subCategory){
        for(Category subField: subCategory){
            if(subField.getCategoryCode().isEmpty() || subField.getCategoryName().isEmpty() || subField.getCategoryDescription().isEmpty())
                return true;
        }
        return false;
    }

    private void throwException(String code, String errorMessage, HttpStatus httpStatus) {
        throw new BusinessException(code, errorMessage ,httpStatus , null);
    }
}
