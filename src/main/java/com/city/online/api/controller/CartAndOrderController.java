package com.city.online.api.controller;

import com.city.online.api.constant.AppConstants;
import com.city.online.api.dto.request.AddToCartRequestDto;
import com.city.online.api.dto.request.CheckoutRequestDto;
import com.city.online.api.dto.response.MessageResponse;
import com.city.online.api.model.Cart;
import com.city.online.api.model.UserOrder;
import com.city.online.api.service.RestaurantService;
import com.city.online.api.validation.validator.common.CityOnlineValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/cart")
public class CartAndOrderController {
    @Autowired
    CityOnlineValidator cityOnlineValidator;
    @Autowired
    RestaurantService restaurantService;

    /* API to SAVE and UPDATE(Handles incrementing and decrementing the count of product) food-items to cart
     * @param - Accepts the single product with the user details
     * */
    @PostMapping(value = "",consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Cart> addFoodItemToCart(@RequestBody @Valid AddToCartRequestDto addToCartRequestDto, Errors errors){
        //RestaurantCustomerCartValidator
        cityOnlineValidator.validate(addToCartRequestDto, AppConstants.RESTAURANT_CUSTOMER_CART_ADD_VALIDATOR,errors);
        Cart cartItems = restaurantService.addToCart(addToCartRequestDto);
        return new ResponseEntity<>(cartItems, HttpStatus.CREATED);
    }

    /* API to remove food-items from cart: cart/remove */
    @DeleteMapping("")
    public ResponseEntity<MessageResponse> deleteCart(@RequestParam(name = "username") String username){
        restaurantService.deleteCartByUsername(username);
        return new ResponseEntity<>(new MessageResponse("Cart deleted for user" + username), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Cart> updateCart(@RequestBody Cart cart){
        Cart cartDB = restaurantService.updateCart(cart);
        return new ResponseEntity<>(cartDB, HttpStatus.OK);
    }

    /* API to get list of cart items */
    @GetMapping("/username")
    public ResponseEntity<Cart> getCartByUsername(@RequestParam(name = "username") String username) {
        Cart cart = restaurantService.displayCartItems(username);
        if(Objects.isNull(cart)) {
            Cart cart1 = new Cart();
            return new ResponseEntity<>(cart1,HttpStatus.OK);
        }
        return new ResponseEntity<>(cart,HttpStatus.OK);
    }

    /* API to place order/checkout */
    @PostMapping(value = "/checkout",consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserOrder> placeOrder(@RequestBody @Valid CheckoutRequestDto checkoutRequestDto, Errors errors){
        //CheckoutValidator
        cityOnlineValidator.validate(checkoutRequestDto,AppConstants.CHECKOUT_VALIDATOR,errors);
        UserOrder checkoutOrder = restaurantService.checkout(checkoutRequestDto);
        restaurantService.deleteCartAfterCheckout(checkoutRequestDto.getUsername());
        return new ResponseEntity<>(checkoutOrder,HttpStatus.OK);
    }


    /* API to get list of cart items */
    @GetMapping("/order/username")
    public ResponseEntity<List<UserOrder>> getOrdersByUsername(@RequestParam(name = "username") String username) {
        List<UserOrder> userOrders = restaurantService.displayOrdersByusername(username);
        if(Objects.isNull(userOrders) || userOrders.isEmpty()) {
            // return an empty list
            userOrders = new ArrayList<>();
            return new ResponseEntity<>(userOrders,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userOrders,HttpStatus.OK);
    }
}
