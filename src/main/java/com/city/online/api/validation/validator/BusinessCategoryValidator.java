package com.city.online.api.validation.validator;

import com.city.online.api.constant.AppConstants;
import com.city.online.api.dto.request.BusinessCategoryCreateRequestDto;
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

@Component
@Slf4j
public class BusinessCategoryValidator implements Validator {
    @Autowired
    BusinessCategoryRepository businessCategoryRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object requestObject, Errors errors){
        BusinessCategoryCreateRequestDto businessCategoryCreateRequestDto = (BusinessCategoryCreateRequestDto) requestObject;

        // user entered details
        Category mainCategory = businessCategoryCreateRequestDto.getMainCategory();
        List<Category> subCategory = businessCategoryCreateRequestDto.getSubCategoryList();

        // get list of all mainCategory codes from database
        List<String> allMainCategoryCodes = getAllMainCategoryCodes();
        
        // check whether any of the mainCategory field is missing
        if(isMainCategoryFieldMissing(mainCategory)){
            throwException(AppConstants.MAIN_CATEGORY_FIELDS_MISSING_ERROR_CODE, AppConstants.MAIN_CATEGORY_FIELDS_MISSING_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }

        // check whether any of the subCategory field is missing
        if(isSubCategoryFieldMissing(subCategory)){
            throwException(AppConstants.SUB_CATEGORY_FIELDS_MISSING_ERROR_CODE, AppConstants.SUB_CATEGORY_FIELDS_MISSING_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }

        // check whether mainCategory entered is unique
        if(allMainCategoryCodes.contains(mainCategory.getCategoryCode())){
            throwException(AppConstants.BUSINESS_CATEGORY_EXISTS_ERROR_CODE,AppConstants.BUSINESS_CATEGORY_EXISTS_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
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
