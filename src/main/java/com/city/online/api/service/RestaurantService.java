package com.city.online.api.service;

import com.city.online.api.constant.AppConstants;
import com.city.online.api.dto.request.*;
import com.city.online.api.exception.BusinessException;
import com.city.online.api.model.*;
import com.city.online.api.model.enums.OrderStatus;
import com.city.online.api.model.enums.RestaurantWorkingStatus;
import com.city.online.api.model.enums.Status;
import com.city.online.api.model.Product;
import com.city.online.api.repository.*;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Builder
@Service
@Slf4j
public class RestaurantService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RestaurantUserRespository restaurantUserRespository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CheckoutRepository checkoutRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;


    //@Async("inventryExecutor")
    public void decrementInventoryCount(Cart cart) {
        //Decrement count for all items present in the request

        for(Product prod : cart.getCartItems()) {
            Optional<Product> optionalProduct = productRepository.findById(prod.getId());
            Product product = optionalProduct.get();
            product.setInventoryCount(optionalProduct.get().getInventoryCount() - prod.getCartCount());
            productRepository.save(product);
        }
    }

    // method to onboard restaurant
    public RestaurantUser onboardRestaurant(RestaurantOnboardRequestDto restaurantOnboardRequestDto){
        try {
            log.info("Creating the restaurant user");
            RestaurantUser restaurantUser = RestaurantUser.builder()
                    .username(restaurantOnboardRequestDto.getUsername())
                    .firstName(restaurantOnboardRequestDto.getFirstName())
                    .lastName(restaurantOnboardRequestDto.getLastName())
                    .ownerMobileNumber(restaurantOnboardRequestDto.getOwnerMobileNumber())
                    .ownerEmailId(restaurantOnboardRequestDto.getOwnerEmailId())
                    .managerName(restaurantOnboardRequestDto.getManagerName())
                    .managerMobileNumber(restaurantOnboardRequestDto.getManagerMobileNumber())
                    .managerEmailId(restaurantOnboardRequestDto.getManagerEmailId())
                    .restaurantDetails(restaurantOnboardRequestDto.getRestaurantDetails())
                    .accountDetails(restaurantOnboardRequestDto.getAccountDetails())
                    .restaurantWorkingStatus(restaurantOnboardRequestDto.getRestaurantWorkingStatus())
                    .status(Status.UNDER_REVIEW)
                    .build();
            return restaurantUserRespository.save(restaurantUser);
        }catch (Exception e){
            throwException(AppConstants.RESTAURANT_ONBOARD_VALIDATOR,"Restaurant onboarding UN-SUCCESSFUL!",HttpStatus.BAD_REQUEST);
            return null;
        }
    }

    // method to create restaurant food menu
    public List<Product> createRestaurantFoodMenu(RestaurantFoodMenuCreateRequestDto restaurantFoodMenuCreateRequestDto) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            List<Product> productList = new ArrayList<>();

            restaurantFoodMenuCreateRequestDto.getFoodItems().stream().forEach(productOnboardDto -> {
                Product product = new Product();
                mapproduct(productOnboardDto, product);
                productList.add(product);
            });
            return productRepository.saveAll(productList);
        } catch (Exception e) {
            throwException(AppConstants.RESTAURANT_ADD_FOOD_MENU_VALIDATOR, "Unable to add restaurant food menu!", HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    private void mapproduct(ProductOnboardDto productOnboardDto, Product product) {
        product.setProductType(productOnboardDto.getProductType());
        product.setCuisineType(productOnboardDto.getCuisineType());
        product.setDescription(productOnboardDto.getDescription());
        product.setFoodImageUrls(productOnboardDto.getFoodImageUrls());
        product.setItemName(productOnboardDto.getItemName());
        product.setStatus(productOnboardDto.getStatus());
        product.setUsername(productOnboardDto.getUsername());
        product.setInventoryCount(productOnboardDto.getInventoryCount());
        product.setItemPrice(productOnboardDto.getItemPrice());
        product.setVeg(productOnboardDto.isVeg());
        product.setVendorId(productOnboardDto.getVendorId());
    }

    // method to add food-item into cart
    // At a time we are storing only one item in the cart
    public Cart addToCart(AddToCartRequestDto addToCartRequestDto) {
        List<Product> cartItemList = new ArrayList<>();
        int cartTotalAmount = 0;

        try {
            Cart cartDB = cartRepository.findByUsername(addToCartRequestDto.getUsername());

            if(null != cartDB) {
                // update Existing Item in the Cart
                boolean isProductAlreadyInCart = cartDB.getCartItems().stream().anyMatch(pr -> pr.getId().equalsIgnoreCase(addToCartRequestDto.getCartItem().getId()));

                if(isProductAlreadyInCart) {
                    // Direct remove the existing entry and put the new entry to avoid the confusion
                    // This method is called only when user is on the ADD TO CART page
                    updateExistingItemInCart(cartDB, addToCartRequestDto);
                    //Update cart total amount
                    for (Product product: cartDB.getCartItems()) {
                        cartTotalAmount = cartTotalAmount + product.getCartPriceTotal();
                    }
                    cartDB.setCartTotalAmount(cartTotalAmount);
                    return cartRepository.save(cartDB);

                } else {
                    //Add the new item in the cart
                    cartDB.getCartItems().add(addToCartRequestDto.getCartItem());
                    cartTotalAmount = cartDB.getCartTotalAmount() + addToCartRequestDto.getCartItem().getCartPriceTotal();
                    cartDB.setCartTotalAmount(cartTotalAmount);
                    return cartRepository.save(cartDB);
                }
            } else {
                //Create the cart for the user
                cartItemList.add(addToCartRequestDto.getCartItem());
                // cart total amount
                Cart cart = Cart.builder()
                        .username(addToCartRequestDto.getUsername())
                        .cartItems(cartItemList)
                        .cartTotalAmount(addToCartRequestDto.getCartItem().getCartPriceTotal())
                        .build();
                return cartRepository.save(cart);
            }
        } catch (Exception e){
            throwException(AppConstants.FOOD_ITEM_NOT_ADDED_ERROR_CODE,AppConstants.FOOD_ITEM_NOT_ADDED_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
            return null;
        }
    }

    private void updateExistingItemInCart(Cart cartDB, AddToCartRequestDto addToCartRequestDto) {
        List<Product> cartItemList = cartDB.getCartItems();

        for (Product product : cartItemList) {
            if(product.getId().equalsIgnoreCase(addToCartRequestDto.getCartItem().getId())) {
                cartItemList.remove(product);
                cartItemList.add(addToCartRequestDto.getCartItem());
                cartDB.setCartItems(cartItemList);
            }
        }
    }

    // method to checkout
    public UserOrder checkout(CheckoutRequestDto checkoutRequestDto) {
        try{
            //Start a new thread
            Cart cart = cartRepository.findCartItemsByUsername(checkoutRequestDto.getUsername());
            User user = userRepository.findUserByUsername(checkoutRequestDto.getUsername());

            // Update the address on each and every order
            user.setAddressList(checkoutRequestDto.getAddressList());
            userRepository.save(user);

            //Decrement inventory count
            decrementInventoryCount(cart);

            for(Product product : cart.getCartItems()) {
                product.setOrderedCount(product.getCartCount());
            }

            UserOrder checkoutOrder = UserOrder.builder()
                    .username(checkoutRequestDto.getUsername())
                    .orderedItems(cart.getCartItems())
                    .orderTotalAmount(cart.getCartTotalAmount())
                    .paymentMode(checkoutRequestDto.getPaymentMode())
                    .orderStatus(OrderStatus.RECEIVED)
                    .deliveryAddress(checkoutRequestDto.getDeliveryAddress())
                    .deliveryMobileNumber(checkoutRequestDto.getDeliveryMobileNumber())
                    .build();
            return checkoutRepository.save(checkoutOrder);
        }catch (Exception e){
            throwException(AppConstants.ORDER_NOT_PLACED_ERROR_CODE, AppConstants.ORDER_NOT_PLACED_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
            return null;
        }
    }


    // method to update status of food order
    public void updateFoodOrderStatus(OrderStatusUpdateRequestDto orderStatusUpdateRequestDto){
        try{
            Optional<UserOrder> checkoutUpdated = checkoutRepository.findById(orderStatusUpdateRequestDto.getId());
            if(checkoutUpdated.isPresent()){
                UserOrder checkoutOrder1 = checkoutUpdated.get();
                checkoutOrder1.setOrderStatus(orderStatusUpdateRequestDto.getUpdateStatus());
                checkoutRepository.save(checkoutOrder1);
            }else {
                throwException(AppConstants.RECORDS_NOT_FOUND_ERROR_CODE,AppConstants.RECORDS_NOT_FOUND_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throwException(AppConstants.ORDER_STATUS_UPDATE_FAILED_ERROR_CODE,AppConstants.ORDER_STATUS_UPDATE_FAILED_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }
    }

    // method to update restaurant details
    public RestaurantUser updateRestaurant(RestaurantUser restaurantOwner){
        try {
            // id and uniqueId should not be updated
            Optional<RestaurantUser> restaurantOwnerUpdated = restaurantUserRespository.findById(restaurantOwner.getId());
            if (restaurantOwnerUpdated.isPresent()) {
                RestaurantUser restaurantOwnerDB = restaurantOwnerUpdated.get();

                restaurantOwnerDB.setFirstName(restaurantOwner.getFirstName());
                restaurantOwnerDB.setLastName(restaurantOwner.getLastName());
                restaurantOwnerDB.setOwnerMobileNumber(restaurantOwner.getOwnerMobileNumber());
                restaurantOwnerDB.setOwnerEmailId(restaurantOwner.getOwnerEmailId());
                restaurantOwnerDB.setManagerName(restaurantOwner.getManagerName());
                restaurantOwnerDB.setManagerMobileNumber(restaurantOwner.getManagerMobileNumber());
                restaurantOwnerDB.setManagerEmailId(restaurantOwner.getManagerEmailId());
                restaurantOwnerDB.setRestaurantDetails(restaurantOwner.getRestaurantDetails());
                restaurantOwnerDB.setAccountDetails(restaurantOwner.getAccountDetails());
                restaurantOwnerDB.setStatus(restaurantOwner.getStatus());
                restaurantOwnerDB.setRestaurantWorkingStatus(restaurantOwner.getRestaurantWorkingStatus());

                //Update the status of products which belong to this restaurant
                if (restaurantOwner.getRestaurantWorkingStatus().equals(RestaurantWorkingStatus.RESTAURANT_CLOSE) || restaurantOwner.getStatus().equals(Status.INACTIVE)) {
                    productService.updateProductStatus(Status.INACTIVE, restaurantOwner.getId());
                } else if (restaurantOwner.getRestaurantWorkingStatus().equals(RestaurantWorkingStatus.RESTAURANT_OPEN) && restaurantOwner.getStatus().equals(Status.ACTIVE)) {
                    productService.updateProductStatus(Status.ACTIVE, restaurantOwner.getId());
                }
                return restaurantUserRespository.save(restaurantOwnerDB);
            } else {
                throwException(AppConstants.RECORDS_NOT_FOUND_ERROR_CODE, AppConstants.RECORDS_NOT_FOUND_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            throwException(AppConstants.RESTAURANT_UPDATE_ERROR_CODE, AppConstants.RESTAURANT_UPDATE_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        return null;
    }

    // method to update restaurant food menu
    public Product updateRestaurantFoodMenu(Product product){
        try {
            return productRepository.save(product);
        } catch (Exception e){
            throwException(AppConstants.RESTAURANT_FOOD_MENU_UPDATE_ERROR_CODE,AppConstants.RESTAURANT_FOOD_MENU_UPDATE_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
            return null;
        }
    }

    // method to display restaurant food menu
    public Page<Product> displayRestaurantFoodMenu(Pageable pageable) {
        try{
            return productRepository.findAll(pageable);
        }catch (Exception e){
            throwException(AppConstants.RECORDS_NOT_FOUND_ERROR_CODE,AppConstants.RECORDS_NOT_FOUND_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
            return null;
        }
    }

    // method to display restaurant food menu by restaurantuserId
    public List<Product> getProductsByRestaurantUserId(String restaurantId) {
        try {
            return productRepository.findByVendorId(restaurantId);
        }catch (Exception e){
            throwException(AppConstants.RECORDS_NOT_FOUND_ERROR_CODE,AppConstants.RECORDS_NOT_FOUND_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
            return Collections.emptyList();
        }
    }

    // method to display list of restaurant names
    public List<RestaurantUser> displayRestaurantNames() {
        try {
            Map<String, String> restaurantNames = new HashMap<>();
            return restaurantUserRespository.findAll();

        }catch (Exception e){
            throwException(AppConstants.RECORDS_NOT_FOUND_ERROR_CODE,AppConstants.RECORDS_NOT_FOUND_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
            return null;
        }
    }

    // method to display list of cart items
    public Cart displayCartItems(String username) {
        return  cartRepository.findByUsername(username);
    }

    // method to remove food item from the cart
    public void deleteCartByUsername(String username) {
        try {
            Cart cartDB = cartRepository.findCartItemsByUsername(username);
            cartRepository.delete(cartDB);
        } catch (Exception e){
            throwException("Cart item unable to remove","Error",HttpStatus.BAD_REQUEST);
        }
    }

    //Update cart for a user
    public Cart updateCart(Cart cart) {
        try {
            log.info("Cart Updated for the user {} ", cart.getUsername());
            return cartRepository.save(cart);
        } catch (Exception e){
            throwException("Cart item unable to remove","Error",HttpStatus.BAD_REQUEST);
        }
        return null;
    }
    private void throwException(String code, String errorMessage, HttpStatus httpStatus) {
        throw new BusinessException(code, errorMessage ,httpStatus , null);
    }

    /*
    * Once the order is placed then delete the entry of the user from the cart
    * */
    public void deleteCartAfterCheckout(String username) {
        cartRepository.deleteByUsername(username);
        log.info("Successfully deleted the cart for user {} ", username);
    }

    public RestaurantUser getRestaurantUserById(String restaurantUserId) {
        return restaurantUserRespository.findById(restaurantUserId).get();
    }

    public RestaurantUser getRestaurantUserByUsername(String restaurantUserId) {
        return restaurantUserRespository.findRestaurantUserByUsername(restaurantUserId);
    }

    public List<UserOrder> displayOrdersByusername(String username) {
        return checkoutRepository.findOrdersByUsername(username);
    }
}
