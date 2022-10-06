package com.city.online.api.controller;

import com.city.online.api.constant.AppConstants;
import com.city.online.api.dto.request.BusinessCategoryCreateRequestDto;
import com.city.online.api.dto.request.BusinessContactUserOnboardRequestDto;
import com.city.online.api.model.BusinessCategory;
import com.city.online.api.model.BusinessContactUser;
import com.city.online.api.service.BusinessContactUserService;
import com.city.online.api.validation.validator.common.CityOnlineValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/business/contact")
public class BusinessContactController {
    @Autowired
    CityOnlineValidator cityOnlineValidator;
    @Autowired
    BusinessContactUserService businessContactUserService;


    /* API to onboard new business user */
    @PostMapping(value = "/user", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BusinessContactUser> onboardBusinessContactUser(@RequestBody @Valid BusinessContactUserOnboardRequestDto businessUserOnboardRequestDto, Errors errors)  {
        //BusinessContactUserOnboardValidator
        cityOnlineValidator.validate(businessUserOnboardRequestDto, AppConstants.BUSINESS_CONTACT_USER_ONBOARD_VALIDATOR, errors);
        BusinessContactUser businessContactUser = businessContactUserService.createBusinessUser(businessUserOnboardRequestDto);
        return new ResponseEntity<>(businessContactUser, HttpStatus.CREATED);
    }

    /* API to create new business category */
    @PostMapping(value = "/category", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BusinessCategory> createBusinessCategory(@RequestBody @Valid BusinessCategoryCreateRequestDto businessCategoryCreateRequestDto, Errors errors){
        cityOnlineValidator.validate(businessCategoryCreateRequestDto, AppConstants.BUSINESS_CATEGORY_VALIDATOR,errors);
        BusinessCategory businessCategory = businessContactUserService.createBusinessCategory(businessCategoryCreateRequestDto);
        return new ResponseEntity<>(businessCategory, HttpStatus.CREATED);
    }

    /* API to fetch business user details by business category & business sub-category*/
    @GetMapping("/category")
    public ResponseEntity<List<BusinessContactUser>> displayUserDetails(@RequestParam(name = "mainBusinessCategory") String mainBusinessCategory){
        List<BusinessContactUser> contactDetails = businessContactUserService.displayBusinessUserDetailsByBusinessMainCategory(mainBusinessCategory);
        return new ResponseEntity<List<BusinessContactUser>>(contactDetails, HttpStatus.OK);
    }

    /*API to get the business profile by username*/
    @GetMapping("/user/username")
    public ResponseEntity<BusinessContactUser> findBusinessContactUserByUsername(@RequestParam(name = "username") String username){
        BusinessContactUser contactDetails = businessContactUserService.findBusinessContactUserByUsername(username);
        if(Objects.nonNull(contactDetails)) {
            return new ResponseEntity<>(contactDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /* API to fetch business user details by status */
    @GetMapping("/user/status")
    public ResponseEntity<List<BusinessContactUser>> displayUserDetailsByStatus(@RequestParam String status){
        List<BusinessContactUser> businessUserDetailsListByStatus = businessContactUserService.displayBusinessUserDetailsByStatus(status);
        return new ResponseEntity<List<BusinessContactUser>>(businessUserDetailsListByStatus, HttpStatus.OK);
    }

    /* API to fetch category details by status */
    @GetMapping("/category/status")
    public ResponseEntity<Page<BusinessCategory>> displayCategoryByStatus(@RequestParam String status,  @RequestParam Integer page, @RequestParam Integer size)  {
        Pageable pageable = PageRequest.of(page, size);
        Page<BusinessCategory> categoryListByStatus = businessContactUserService.displayCategoryByStatus(status, pageable);
        return new ResponseEntity<>(categoryListByStatus, HttpStatus.OK);
    }

    /* API to update category */
    @PutMapping(value = "/category", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BusinessCategory> updateCategory(@Valid @RequestBody BusinessCategory businessCategory, Errors errors){
        cityOnlineValidator.validate(businessCategory, AppConstants.BUSINESS_CATEGORY_UPDATE_VALIDATOR,errors);
        BusinessCategory businessCategoryUpdated = businessContactUserService.updateBusinessCategory(businessCategory);
        return new ResponseEntity<BusinessCategory>(businessCategoryUpdated,HttpStatus.OK);
    }

    /* API to update business user */
    @PutMapping(value = "/user", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BusinessContactUser> updateBusinessUser(@Valid @RequestBody BusinessContactUser businessContactUser, Errors errors){
        cityOnlineValidator.validate(businessContactUser, AppConstants.BUSINESS_CONTACT_USER_UPDATE_VALIDATOR,errors);
        BusinessContactUser businessContactUpdateUser = businessContactUserService.updateBusinessUser(businessContactUser);
        return new ResponseEntity<BusinessContactUser>(businessContactUpdateUser,HttpStatus.OK);
    }

    /* API to fetch all categories present to be displayed in the dropdown*/
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categoryListByStatus = businessContactUserService.fetchAllCategories();
        return new ResponseEntity<>(categoryListByStatus, HttpStatus.OK);
    }
}
