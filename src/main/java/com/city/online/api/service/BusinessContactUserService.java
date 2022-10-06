package com.city.online.api.service;

import com.city.online.api.constant.AppConstants;
import com.city.online.api.dto.request.BusinessCategoryCreateRequestDto;
import com.city.online.api.dto.request.BusinessContactUserOnboardRequestDto;
import com.city.online.api.exception.BusinessException;
import com.city.online.api.model.BusinessCategory;
import com.city.online.api.model.BusinessContactUser;
import com.city.online.api.model.enums.Status;
import com.city.online.api.model.pojo.Category;
import com.city.online.api.repository.BusinessCategoryRepository;
import com.city.online.api.repository.BusinessContactUserRepository;
import com.city.online.api.repository.UserRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
@Service
@Slf4j
public class BusinessContactUserService {
    @Autowired
    BusinessCategoryRepository businessCategoryRepository;
    @Autowired
    BusinessContactUserRepository businessContactUserRepository;
    @Autowired
    UserRepository userRepository;

    // method to create business category
    public BusinessCategory createBusinessCategory(BusinessCategoryCreateRequestDto businessCategoryCreateRequestDto){

        try {
            BusinessCategory businessCategory = new BusinessCategory();

            businessCategory.setMainCategory(businessCategoryCreateRequestDto.getMainCategory());
            businessCategory.setSubCategoryList((List<Category>) businessCategoryCreateRequestDto.getSubCategoryList());
            businessCategory.getMainCategory().setStatus(Status.UNDER_REVIEW);

            return businessCategoryRepository.save(businessCategory);
        }
        catch (Exception e) {
            throwException(AppConstants.BUSINESS_CATEGORY_ERROR_CODE,AppConstants.BUSINESS_CATEGORY_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
            return null;
        }
    }

    // method to create a business user
    public BusinessContactUser createBusinessUser(BusinessContactUserOnboardRequestDto businessUserOnboardRequestDto) {

            try {
                // onboard a new businessContactUser
                BusinessContactUser businessContactUser = BusinessContactUser.builder()
                        .username(businessUserOnboardRequestDto.getUsername())
                        .firstName(businessUserOnboardRequestDto.getFirstName())
                        .lastName(businessUserOnboardRequestDto.getLastName())
                        .emailId(businessUserOnboardRequestDto.getEmailId())
                        .mobileNumber1(businessUserOnboardRequestDto.getMobileNumber1())
                        .mobileNumber2(businessUserOnboardRequestDto.getMobileNumber2())
                        .businessName(businessUserOnboardRequestDto.getBusinessName())
                        .businessDescription(businessUserOnboardRequestDto.getBusinessDescription())
                        .businessDescription(businessUserOnboardRequestDto.getAddress())
                        .streetName(businessUserOnboardRequestDto.getStreetName())
                        .cityName(businessUserOnboardRequestDto.getCityName())
                        .stateName(businessUserOnboardRequestDto.getStateName())
                        .startTime(businessUserOnboardRequestDto.getStartTime())
                        .endTime(businessUserOnboardRequestDto.getEndTime())
                        .holiday(businessUserOnboardRequestDto.getHoliday())
                        .mainBusinessCategory(businessUserOnboardRequestDto.getMainBusinessCategory())
                        .subBusinessCategoryList(businessUserOnboardRequestDto.getSubBusinessCategoryList())
                        .status(Status.UNDER_REVIEW) //default status
                        .build();
                return businessContactUserRepository.save(businessContactUser);
            } catch (Exception e) {
                throwException(AppConstants.USER_VALIDATION_ERROR_CODE, AppConstants.USER_VALIDATION_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
            }
        return null;
    }

    // method to update category fields
    public BusinessCategory updateBusinessCategory(BusinessCategory businessCategory) {

        try {
            Optional<BusinessCategory> businessCategoryUpdated = businessCategoryRepository.findById(businessCategory.getId());

            BusinessCategory businessCategory1 = businessCategoryUpdated.get();
            businessCategory1.setMainCategory(businessCategory.getMainCategory());
            businessCategory1.setSubCategoryList(businessCategory.getSubCategoryList());
            return businessCategoryRepository.save(businessCategory1);
        }
        catch (Exception e){
            throwException(AppConstants.BUSINESS_CATEGORY_UPDATE_ERROR_CODE, AppConstants.BUSINESS_CATEGORY_UPDATE_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
            return null;
        }
    }

    // method to update business user fields
    public BusinessContactUser updateBusinessUser(BusinessContactUser businessContactUser) {

        try{

            /*Optional<BusinessContactUser> businessContactUserUpdated = businessContactUserRepository.findById(businessContactUser.getId());
            BusinessContactUser businessContactUser1 = businessContactUserUpdated.get();

            businessContactUser1.setUsername(businessContactUser.getUsername());
            businessContactUser1.setFirstName(businessContactUser.getFirstName());
            businessContactUser1.setLastName(businessContactUser.getLastName());
            businessContactUser1.setEmailId(businessContactUser.getEmailId());
            businessContactUser1.setMobileNumber1(businessContactUser.getMobileNumber1());
            businessContactUser1.setMobileNumber2(businessContactUser.getMobileNumber2());
            businessContactUser1.setBusinessName(businessContactUser.getBusinessName());
            businessContactUser1.setStreetName(businessContactUser.getStreetName());
            businessContactUser1.setCityName(businessContactUser.getCityName());
            businessContactUser1.setStateName(businessContactUser.getStateName());
            businessContactUser1.setMainBusinessCategory(businessContactUser.getMainBusinessCategory());
            businessContactUser1.setSubBusinessCategoryList(businessContactUser.getSubBusinessCategoryList());
            businessContactUser1.setStartTime(businessContactUser.getStartTime());
            businessContactUser1.setEndTime(businessContactUser.getEndTime());
            businessContactUser1.setHoliday(businessContactUser.getHoliday());
            businessContactUser1.setStatus(businessContactUser.getStatus());*/

            return businessContactUserRepository.save(businessContactUser);
        }catch (Exception e){
            throwException(AppConstants.BUSINESS_CONTACT_USER_UPDATE_ERROR_CODE, AppConstants.BUSINESS_CONTACT_USER_UPDATE_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
            return null;
        }
    }

    // method to get business user details by category
    public List<BusinessContactUser> displayBusinessUserDetailsByBusinessMainCategory(String mainBusinessCategory) {
        try {
            List<BusinessContactUser> contactDetails = businessContactUserRepository.findBusinessUserDetailsByBusinessMainCategory(mainBusinessCategory, Status.ACTIVE.name());
            if(contactDetails.size() == 0){
                throwException(AppConstants.RECORDS_NOT_FOUND_ERROR_CODE, AppConstants.RECORDS_NOT_FOUND_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
                return null;
            }
            return contactDetails;
        } catch (Exception e){
            throwException(AppConstants.RECORDS_NOT_FOUND_ERROR_CODE, AppConstants.RECORDS_NOT_FOUND_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
            return null;
        }
    }

    // method to get business user details by its status
    public List<BusinessContactUser> displayBusinessUserDetailsByStatus(String status) {
        try {
            List<BusinessContactUser> businessUserDetailsListByStatus = businessContactUserRepository.findBusinessUserDetailsByStatus(status);
            if(businessUserDetailsListByStatus.size() == 0){
                throwException(AppConstants.RECORDS_NOT_FOUND_ERROR_CODE, AppConstants.RECORDS_NOT_FOUND_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
                return null;
            }
            return businessUserDetailsListByStatus;
        } catch (Exception e){
            throwException(AppConstants.RECORDS_NOT_FOUND_ERROR_CODE, AppConstants.RECORDS_NOT_FOUND_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
            return null;
        }
    }

    // method to get category details by its status
    public Page<BusinessCategory> displayCategoryByStatus(String status, Pageable pageable) {
        try {
            Page<BusinessCategory> categoryListByStatus = businessCategoryRepository.findCategoryByStatus(status, pageable);
            if(categoryListByStatus.getTotalPages() == 0){
                throwException(AppConstants.RECORDS_NOT_FOUND_ERROR_CODE, AppConstants.RECORDS_NOT_FOUND_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
                return null;
            }
            return categoryListByStatus;
        } catch (Exception e){
            throwException(AppConstants.RECORDS_NOT_FOUND_ERROR_CODE, AppConstants.RECORDS_NOT_FOUND_ERROR_MESSAGE,HttpStatus.INTERNAL_SERVER_ERROR);
            return null;
        }
    }

    //Method to fetch all of the categories
    public List<String> fetchAllCategories() {
        List<BusinessCategory> businessCategories = businessCategoryRepository.findAll();
        return businessCategories.stream().map(bcat -> bcat.getMainCategory().getCategoryName()).collect(Collectors.toList());
    }

    public BusinessContactUser findBusinessContactUserByUsername(String username) {
       return businessContactUserRepository.findBusinessUserDetailsByUsername(username);
    }

    private void throwException(String code, String errorMessage, HttpStatus httpStatus) {
        throw new BusinessException(code, errorMessage ,httpStatus , null);
    }
}
