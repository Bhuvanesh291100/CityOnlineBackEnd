package com.city.online.api.controller;

import com.city.online.api.constant.AppConstants;
import com.city.online.api.dto.request.*;
import com.city.online.api.model.RestaurantUser;
import com.city.online.api.model.Product;
import com.city.online.api.service.RestaurantService;
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

@RestController
@RequestMapping("/restaurant")
public class FoodRestaurantController {
    @Autowired
    CityOnlineValidator cityOnlineValidator;
    @Autowired
    RestaurantService restaurantService;


    /* API to onboard new restaurant / restaurant user */
    @PostMapping(value = "/user", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestaurantUser> onboardRestaurant(@RequestBody @Valid RestaurantOnboardRequestDto restaurantOnboardRequestDto, Errors errors) {
        //RestaurantOnboardValidator
        cityOnlineValidator.validate(restaurantOnboardRequestDto, AppConstants.RESTAURANT_ONBOARD_VALIDATOR,errors);
        RestaurantUser RestaurantUser = restaurantService.onboardRestaurant(restaurantOnboardRequestDto);
        return new ResponseEntity<>(RestaurantUser, HttpStatus.CREATED);
    }

    /* API to get a restaurant user by username*/
    @GetMapping(value = "/username", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestaurantUser> getRestaurantByUsername(@RequestParam(name = "username") String username) {
        RestaurantUser RestaurantUser = restaurantService.getRestaurantUserByUsername(username);
        return new ResponseEntity<>(RestaurantUser, HttpStatus.CREATED);
    }

    /* API to get a restaurant user bu restaurantUserid*/
    @GetMapping(value = "/id", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestaurantUser> getUserByRestaurantUserId(@RequestParam(name = "restaurantUserId") String restaurantUserId) {
        RestaurantUser RestaurantUser = restaurantService.getRestaurantUserById(restaurantUserId);
        return new ResponseEntity<>(RestaurantUser, HttpStatus.CREATED);
    }

    /* API to update restaurant details */
    @PutMapping(value = "/user", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestaurantUser> updateRestaurant(@RequestBody @Valid RestaurantUser RestaurantUser, Errors errors) {
        cityOnlineValidator.validate(RestaurantUser, AppConstants.RESTAURANT_UPDATE_VALIDATOR,errors);
        RestaurantUser RestaurantUser1 = restaurantService.updateRestaurant(RestaurantUser);
        return new ResponseEntity<>(RestaurantUser1, HttpStatus.CREATED);
    }

    /* API to get restaurant-name list */
    @GetMapping("/all")
    public ResponseEntity<List<RestaurantUser>> displayRestaurants() {
        List<RestaurantUser> restaurantList = restaurantService.displayRestaurantNames();
        return new ResponseEntity<>(restaurantList,HttpStatus.OK);
    }

    /* API to create restaurant food menu */
    @PostMapping(value = "/menu", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Product>> createRestaurantFoodMenu(@RequestBody @Valid RestaurantFoodMenuCreateRequestDto restaurantFoodMenuCreateRequestDto, Errors errors){
        //RestaurantFoodMenuValidator
        cityOnlineValidator.validate(restaurantFoodMenuCreateRequestDto, AppConstants.RESTAURANT_ADD_FOOD_MENU_VALIDATOR,errors);
        List<Product> restaurantFoodMenu = restaurantService.createRestaurantFoodMenu(restaurantFoodMenuCreateRequestDto);
        return new ResponseEntity<>(restaurantFoodMenu, HttpStatus.CREATED);
    }


    /* API to get food menus of all the restaurants */
    @GetMapping("/menu")
    public ResponseEntity<Page<Product>> getAllRestaurantFoodMenu(@RequestParam Integer page, @RequestParam Integer size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> restaurantFoodMenus = restaurantService.displayRestaurantFoodMenu(pageable);
        return new ResponseEntity<>(restaurantFoodMenus, HttpStatus.OK);
    }

    /* API to get restaurant specific food menu */
    @GetMapping("/menu/id")
    public ResponseEntity<List<Product>> displayRestaurantFoodMenuByRestaurantId(@RequestParam String restaurantId){
        List<Product> foodItemList = restaurantService.getProductsByRestaurantUserId(restaurantId);
        return new ResponseEntity<>(foodItemList, HttpStatus.OK);
    }

    /* API to update single item in the restaurant food menu */
    @PutMapping(value = "/menu", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> updateRestaurantFoodMenu(@RequestBody @Valid Product restaurantFoodMenu, Errors errors){
        cityOnlineValidator.validate(restaurantFoodMenu, AppConstants.RESTAURANT_FOOD_MENU_UPDATE_VALIDATOR,errors);
        Product restaurantFoodMenu1 = restaurantService.updateRestaurantFoodMenu(restaurantFoodMenu);
        return new ResponseEntity<>(restaurantFoodMenu1, HttpStatus.CREATED);
    }




    /* API to update food order status */
    @PutMapping(value = "/order/status")
    public ResponseEntity<String> updateFoodOrderStatus(@RequestBody @Valid OrderStatusUpdateRequestDto orderStatusUpdateRequestDto, Errors errors) {
        cityOnlineValidator.validate(orderStatusUpdateRequestDto, AppConstants.RESTAURANT_FOOD_ORDER_STATUS_VALIDATOR,errors);
        restaurantService.updateFoodOrderStatus(orderStatusUpdateRequestDto);
        return new ResponseEntity<>("Status updated successfully!", HttpStatus.CREATED);
    }

}
