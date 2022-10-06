package com.city.online.api.constant;

public class AppConstants {
    public static final String AUTH_VALIDATOR = "AUTH_VALIDATOR";
    public static final String BUSINESS_CONTACT_USER_ONBOARD_VALIDATOR = "BUSINESS_CONTACT_USER_ONBOARD_VALIDATOR";
    public static final String BUSINESS_CONTACT_USER_UPDATE_VALIDATOR = "BUSINESS_CONTACT_USER_UPDATE_VALIDATOR";
    public static final String BUSINESS_CATEGORY_VALIDATOR = "BUSINESS_CATEGORY_VALIDATOR";
    public static final String BUSINESS_CATEGORY_UPDATE_VALIDATOR = "BUSINESS_CATEGORY_UPDATE_VALIDATOR";
    public static final String RESTAURANT_ONBOARD_VALIDATOR = "RESTAURANT_ONBOARD_VALIDATOR";
    public static final String RESTAURANT_UPDATE_VALIDATOR = "RESTAURANT_UPDATE_VALIDATOR";
    public static final String RESTAURANT_ADD_FOOD_MENU_VALIDATOR = "RESTAURANT_FOOD_MENU_VALIDATOR";
    public static final String RESTAURANT_FOOD_MENU_UPDATE_VALIDATOR = "RESTAURANT_FOOD_MENU_UPDATE_VALIDATOR";
    public static final String RESTAURANT_CUSTOMER_CART_ADD_VALIDATOR = "RESTAURANT_CUSTOMER_CART_ADD_VALIDATOR";
    public static final String CHECKOUT_VALIDATOR = "CHECKOUT_VALIDATOR";
    public static final String RESTAURANT_FOOD_ORDER_STATUS_VALIDATOR = "RESTAURANT_FOOD_ORDER_STATUS_VALIDATOR";

    public static final String USER_VALIDATION_ERROR_CODE = "USER_VALIDATION_ERROR_CODE";
    public static final String USER_VALIDATION_ERROR_MESSAGE = "Not valid username";

    public static final String CHECKOUT_ADDRESS_VALIDATION_ERROR_CODE = "USER_VALIDATION_ERROR_CODE";
    public static final String CHECKOUT_ADDRESS_ERROR_MESSAGE = "Not valid username";
    public static final String QUIZ_VALIDATION_ERROR_CODE = "QUIZ_VALIDATION_ERROR_CODE";
    public static final String QUIZ_USER_ONBOARD_VALIDATOR = "QUIZ_USER_ONBOARD_VALIDATOR";
    public static final String QUIZ_VALIDATION_ERROR_MESSAGE = "Not valid quiz";
    public static final String QUIZ_VALIDATION_CODE = "QUIZ_VALIDATION_CODE";
    public static final String QUIZ_VALIDATION_MESSAGE = "Quiz Exist";
    public static final String RESTAURANT_USER_VALIDATION_ERROR_CODE = "USER_VALIDATION_ERROR_CODE";
    public static final String RESTAURANT_USER_VALIDATION_ERROR_MESSAGE = "Username doesn't exists!";

    public static final String EMAIL_DUPLICATION_ERROR_CODE = "EMAIL_DUPLICATION_ERROR_CODE";
    public static final String EMAIL_DUPLICATION_ERROR_MESSAGE = "Email is already in use!";

    public static final String USER_DUPLICATION_ERROR_CODE = "EMAIL_DUPLICATION_ERROR_CODE";
    public static final String USER_DUPLICATION_ERROR_MESSAGE = "Email is already in use!";
    public static final String EMAIL_FORMAT_ERROR_CODE = "EMAIL_FORMAT_ERROR_CODE";
    public static final String EMAIL_FORMAT_ERROR_MESSAGE = "Please enter valid email address!";
    public static final String MOBILE_NUMBER_INVALID_ERROR_CODE = "MOBILE_NUMBER_VALIDATION_ERROR_CODE";
    public static final String MOBILE_NUMBER_INVALID_ERROR_MESSAGE = "Mobile number MUST contain 10 digits only!";
    public static final String MISSING_REQUIRED_FIELD_ERROR_CODE = "MISSING_REQUIRED_FIELD_ERROR_CODE";
    public static final String MISSING_REQUIRED_FIELD_ERROR_MESSAGE = "Please fill all the mandatory fields!";
    public static final String AUTH_SIGN_UP_VALIDATOR_ERROR_CODE = "SIGN_UP_API_INPUT_VALIDATION_ERROR";
    public static final String BUSINESS_CATEGORY_ERROR_CODE = "BUSINESS_CATEGORY_ERROR_CODE";
    public static final String BUSINESS_CATEGORY_ERROR_MESSAGE = "Please enter valid business category!";
    public static final String BUSINESS_SUB_CATEGORY_ERROR_CODE = "BUSINESS_SUB_CATEGORY_ERROR_CODE";
    public static final String BUSINESS_SUB_CATEGORY_ERROR_MESSAGE = "Please enter valid sub-category/sub-categories!";
    public static final String BUSINESS_CATEGORY_EXISTS_ERROR_CODE = "BUSINESS_CATEGORY_EXISTS_ERROR_CODE";
    public static final String BUSINESS_CATEGORY_EXISTS_ERROR_MESSAGE = "Business category with same name already exists!";
    public static final String MAIN_BUSINESS_CATEGORY_DOESNT_EXISTS_ERROR_CODE = "MAIN_BUSINESS_CATEGORY_DOESNT_EXISTS_ERROR_CODE";
    public static final String MAIN_BUSINESS_CATEGORY_DOESNT_EXISTS_ERROR_MESSAGE = "Main Business category doesn't exists!";
    public static final String SUB_BUSINESS_CATEGORY_DOESNT_EXISTS_ERROR_CODE = "SUB_BUSINESS_CATEGORY_DOESNT_EXISTS_ERROR_CODE";
    public static final String SUB_BUSINESS_CATEGORY_DOESNT_EXISTS_ERROR_MESSAGE = "Sub Business category doesn't exists!";
    public static final String MAIN_CATEGORY_FIELDS_MISSING_ERROR_CODE = "MAIN_CATEGORY_FIELDS_MISSING_ERROR_CODE";
    public static final String MAIN_CATEGORY_FIELDS_MISSING_ERROR_MESSAGE = "Please enter all main category fields";
    public static final String SUB_CATEGORY_FIELDS_MISSING_ERROR_CODE = "SUB_CATEGORY_FIELDS_MISSING_ERROR_CODE";
    public static final String SUB_CATEGORY_FIELDS_MISSING_ERROR_MESSAGE = "Please enter atleast one sub-category";
    public static final String BUSINESS_CONTACT_USER_UPDATE_ERROR_CODE = "BUSINESS_CONTACT_USER_UPDATE_ERROR_CODE";
    public static final String BUSINESS_CONTACT_USER_UPDATE_ERROR_MESSAGE = "Business user failed to update!";
    public static final String BUSINESS_CATEGORY_UPDATE_ERROR_CODE = "BUSINESS_CONTACT_USER_UPDATE_ERROR_CODE";
    public static final String BUSINESS_CATEGORY_UPDATE_ERROR_MESSAGE = "Business user failed to update!";
    public static final String RECORDS_NOT_FOUND_ERROR_CODE = "RECORDS_NOT_FOUND_ERROR_CODE";
    public static final String RECORDS_NOT_FOUND_ERROR_MESSAGE = "No matching records found in database!";
    public static final String PINCODE_ERROR_CODE = "PINCODE_ERROR_CODE";
    public static final String PINCODE_ERROR_MESSAGE = "Kindly enter valid 6 digit pincode";
    public static final String PAN_NUMBER_ERROR_CODE = "PAN_NUMBER_ERROR_CODE";
    public static final String PAN_NUMBER_ERROR_MESSAGE = "Kindly enter 10 digit pan number";
    public static final String INVALID_URL_ERROR_CODE = "INVALID_URL_ERROR_CODE";
    public static final String INVALID_URL_ERROR_MESSAGE = "URL entered is invalid!";
    public static final String INVALID_WEEKDAYS_ERROR_CODE = "INVALID_WEEKDAYS_ERROR_CODE";
    public static final String INVALID_WEEKDAYS_ERROR_MESSAGE = "Kindly enter valid week days!";
    public static final String UNIQUE_ID_DOESNT_EXISTS_ERROR_CODE = "UNIQUE_ID_DOESNT_EXISTS_ERROR_CODE";
    public static final String UNIQUE_ID_DOESNT_EXISTS_ERROR_MESSAGE = "UniqueId is not registered!";
    public static final String RESTAURANT_UPDATE_ERROR_CODE = "RESTAURANT_UPDATE_ERROR_CODE";
    public static final String RESTAURANT_UPDATE_ERROR_MESSAGE = "Restaurant details failed to update!";
    public static final String RESTAURANT_FOOD_MENU_UPDATE_ERROR_CODE = "RESTAURANT_FOOD_MENU_UPDATE_ERROR_CODE";
    public static final String RESTAURANT_FOOD_MENU_UPDATE_ERROR_MESSAGE = "Restaurant food menu failed to update!";
    public static final String ORDER_NOT_PLACED_ERROR_CODE = "ORDER_NOT_PLACED_ERROR_CODE";
    public static final String ORDER_NOT_PLACED_ERROR_MESSAGE = "Order couldn't be placed!, checkout failed";
    public static final String RESTAURANT_DOEST_EXIST_ERROR_CODE = "RESTAURANT_DOEST_EXIST_ERROR_CODE";
    public static final String RESTAURANT_DOEST_EXIST_ERROR_MESSAGE = "Kindly enter valid restaurant name!";
    public static final String FOOD_ITEM_NOT_ADDED_ERROR_CODE = "FOOD_ITEM_NOT_ADDED_ERROR_CODE";
    public static final String FOOD_ITEM_NOT_ADDED_ERROR_MESSAGE = "Couldn't add items to the cart!";
    public static final String DUPLICATE_RESTAURANT_ID_ERROR_CODE = "DUPLICATE_RESTAURANT_ID_ERROR_CODE";
    public static final String DUPLICATE_RESTAURANT_ID_ERROR_MESSAGE = "RestaurantId already exists! Kindly try to UPDATE record or enter another restaurantId";
    public static final String ORDER_STATUS_UPDATE_FAILED_ERROR_CODE = "ORDER_STATUS_UPDATE_FAILED_ERROR_CODE";
    public static final String ORDER_STATUS_UPDATE_FAILED_ERROR_MESSAGE = "Food order status failed to update!";
    public static final String INVALID_ID_ERROR_CODE = "INVALID_ID_ERROR_CODE";
    public static final String INVALID_ID__ERROR_MESSAGE = "Kindly enter the valid id!";
    public static final String INVALID_CHECKOUT_ID_ERROR_CODE = "INVALID_CHECKOUT_ID_ERROR_CODE";
    public static final String INVALID_CHECKOUT_ID__ERROR_MESSAGE = "Kindly enter the valid checkout id!";
    public static final String RESTAURANT_CLOSED_ERROR_CODE = "RESTAURANT_CLOSED_ERROR_CODE";
    public static final String RESTAURANT_CLOSED_ERROR_MESSAGE = "Restaurant not accepting further orders!";
    public static final String FOOD_ITEM_OUT_OF_STOCK_ERROR_CODE = "FOOD_ITEM_OUT_OF_STOCK_ERROR_CODE";
    public static final String FOOD_ITEM_OUT_OF_STOCK_ERROR_MESSAGE = "Dish is out of stock!";
    public static final String QUESTION_FIELDS_MISSING_ERROR_CODE = "QUESTION_FIELDS_MISSING_ERROR_CODE";
    public static final String QUESTION_FIELDS_MISSING_ERROR_MESSAGE = "Please enter all question fields";
    public static final String OPTION_FIELDS_MISSING_ERROR_CODE = "OPTION_FIELDS_MISSING_ERROR_CODE";
    public static final String OPTION_FIELDS_MISSING_ERROR_MESSAGE = "Please enter all option field";
    public static final String QUIZ_VALIDATOR = "QUIZ_VALIDATOR";

    public static final String OPTION_SUBMIT_DUPLICATE_ERROR_MESSAGE = "User has already submitted the Quiz.";
    public static final String OPTION_SUBMIT_DUPLICATE_ERROR_CODE = "QUIZ_ALREADY_SUBMITTED";
    public static final String QUIZ_SUBMIT_VALIDATION_ERROR_MESSAGE = "Please Submit all mandatory fields";
    public static final String QUIZ_SUBMIT_VALIDATION_ERROR_CODE = "QUIZ_SUBMIT_VALIDATION_CODE";
    public static final String QUIZ_ANSWER_SUBMIT_VALIDATOR = "QUIZ_ANSWER_SUBMIT_VALIDATOR";

    public static final String PRODUCT_GET_ERROR_CODE = "PRODUCT_GET_ERROR_CODE";
    public static final String PRODUCT_GET_ERROR_MESSAGE = "Error while fetching the products";
    public static final String PRODUCT_UPDATE_STATUS_ERROR_CODE = "PRODUCT_UPDATE_STATUS_ERROR_CODE";
    public static final String PRODUCT_UPDATE_STATUS_ERROR_MESSAGE = "Error while updating the status of products";
    public static final String ADVERTISEMENT_VALIDATOR = "Advertisement Validated";

    public static final String ADVERTISEMENT_ERROR = "Error fetching Advertisement";

    public static final String ADVERTISEMENT_GET_ERROR_CODE = "ADVERTISEMENT_GET_ERROR_CODE";
}

