package com.city.online.api.validation.validator;

import com.city.online.api.constant.AppConstants;
import com.city.online.api.exception.BusinessException;
import com.city.online.api.model.Product;
import com.city.online.api.repository.ProductRepository;
import com.city.online.api.repository.RestaurantUserRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class RestaurantFoodMenuUpdateValidator implements Validator {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    RestaurantUserRespository restaurantUserRespository;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object requestObject, Errors errors){
        Product product = (Product) requestObject;
        Optional<Product> optionalProduct = productRepository.findById(product.getId());

        // check whether valid 'id' is entered
        if(optionalProduct.isPresent()){
            throwException(AppConstants.RECORDS_NOT_FOUND_ERROR_CODE, AppConstants.RECORDS_NOT_FOUND_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }

        // check if any required field is missing
        if (product.getVendorId().isEmpty() ||
                product.getUsername().isEmpty() || Objects.isNull(product.getInventoryCount()) || Objects.isNull(product.getItemPrice())) {
            throwException(AppConstants.MISSING_REQUIRED_FIELD_ERROR_CODE, AppConstants.MISSING_REQUIRED_FIELD_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }



        // photo url validation
        if(!isValidURL(product.getFoodImageUrls())){
            throwException(AppConstants.INVALID_URL_ERROR_CODE,AppConstants.INVALID_URL_ERROR_MESSAGE,HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isValidURL(List<String> imageUrls){

        try{
                for (String url : imageUrls) {
                    new URL(url).toURI();
                }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private void throwException(String code, String errorMessage, HttpStatus httpStatus) {
        throw new BusinessException(code, errorMessage ,httpStatus , null);
    }
}
