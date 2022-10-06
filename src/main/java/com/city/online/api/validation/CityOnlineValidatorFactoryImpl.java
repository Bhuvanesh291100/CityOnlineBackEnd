package com.city.online.api.validation;

import com.city.online.api.validation.validator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

@Service
public class CityOnlineValidatorFactoryImpl {

    @Autowired
    private AuthValidator authValidator;
    @Autowired
    private RestaurantOnboardValidator restaurantOnboardValidator;
    @Autowired
    private RestaurantUpdateValidator restaurantUpdateValidator;
    @Autowired
    private RestaurantFoodMenuValidator restaurantFoodMenuValidator;
    @Autowired
    private RestaurantFoodMenuUpdateValidator restaurantFoodMenuUpdateValidator;
    @Autowired
    private BusinessContactUserOnboardValidator businessContactUserOnboardValidator;
    @Autowired
    private BusinessContactUserUpdateValidator businessContactUserUpdateValidator;
    @Autowired
    private BusinessCategoryValidator businessCategoryValidator;
    @Autowired
    private BusinessCategoryUpdateValidator businessCategoryUpdateValidator;
    @Autowired
    private QuizUserOnboardValidator quizUserOnboardValidator;
    @Autowired
    private RestaurantCustomerCartValidator restaurantCustomerCartValidator;
    @Autowired
    private CheckoutValidator checkoutValidator;
    @Autowired
    private UpdateOrderStatusValidator updateOrderStatusValidator;

    @Autowired
    private QuizValidator quizValidator;

    @Autowired
    private QuizAnswerSubmitValidator quizAnswerSubmitValidator;

    private final String AUTH_VALIDATOR = "AUTH_VALIDATOR";
    private final String BUSINESS_CONTACT_USER_ONBOARD_VALIDATOR = "BUSINESS_CONTACT_USER_ONBOARD_VALIDATOR";
    private final String BUSINESS_CONTACT_USER_UPDATE_VALIDATOR = "BUSINESS_CONTACT_USER_UPDATE_VALIDATOR";
    private final String BUSINESS_CATEGORY_VALIDATOR = "BUSINESS_CATEGORY_VALIDATOR";
    private final String BUSINESS_CATEGORY_UPDATE_VALIDATOR = "BUSINESS_CATEGORY_UPDATE_VALIDATOR";
    private final String QUIZ_USER_ONBOARD_VALIDATOR = "QUIZ_USER_ONBOARD_VALIDATOR";
    private final String QUIZ_VALIDATOR = "QUIZ_VALIDATOR";
    private final String QUIZ_ANSWER_SUBMIT_VALIDATOR = "QUIZ_ANSWER_SUBMIT_VALIDATOR";

    private final String RESTAURANT_ONBOARD_VALIDATOR = "RESTAURANT_ONBOARD_VALIDATOR";
    private final String RESTAURANT_UPDATE_VALIDATOR = "RESTAURANT_UPDATE_VALIDATOR";
    private final String RESTAURANT_ADD_FOOD_MENU_VALIDATOR = "RESTAURANT_FOOD_MENU_VALIDATOR";
    private final String RESTAURANT_FOOD_MENU_UPDATE_VALIDATOR = "RESTAURANT_FOOD_MENU_UPDATE_VALIDATOR";
    private final String RESTAURANT_CUSTOMER_CART_ADD_VALIDATOR = "RESTAURANT_CUSTOMER_CART_ADD_VALIDATOR";
    private final String CHECKOUT_VALIDATOR = "CHECKOUT_VALIDATOR";
    private final String RESTAURANT_FOOD_ORDER_STATUS_VALIDATOR = "RESTAURANT_FOOD_ORDER_STATUS_VALIDATOR";

    public Validator getValidator(String validatorName) {
        switch (validatorName) {
            case AUTH_VALIDATOR:
                return authValidator;
            case BUSINESS_CONTACT_USER_ONBOARD_VALIDATOR:
                return businessContactUserOnboardValidator;
            case BUSINESS_CONTACT_USER_UPDATE_VALIDATOR:
                return businessContactUserUpdateValidator;
            case BUSINESS_CATEGORY_VALIDATOR:
                return businessCategoryValidator;
            case BUSINESS_CATEGORY_UPDATE_VALIDATOR:
                return businessCategoryUpdateValidator;
            case QUIZ_USER_ONBOARD_VALIDATOR:
                return quizUserOnboardValidator;
            case QUIZ_VALIDATOR:
                 return quizValidator;
            case QUIZ_ANSWER_SUBMIT_VALIDATOR:
                return quizAnswerSubmitValidator;
            case RESTAURANT_ONBOARD_VALIDATOR:
                return restaurantOnboardValidator;
            case RESTAURANT_ADD_FOOD_MENU_VALIDATOR:
                return restaurantFoodMenuValidator;
            case RESTAURANT_UPDATE_VALIDATOR:
                return restaurantUpdateValidator;
            case RESTAURANT_FOOD_MENU_UPDATE_VALIDATOR:
                return restaurantFoodMenuUpdateValidator;
            case RESTAURANT_CUSTOMER_CART_ADD_VALIDATOR:
                return restaurantCustomerCartValidator;
            case CHECKOUT_VALIDATOR:
                return checkoutValidator;
            case RESTAURANT_FOOD_ORDER_STATUS_VALIDATOR:
                return updateOrderStatusValidator;
            default:
                return null;
        }
    }
}
