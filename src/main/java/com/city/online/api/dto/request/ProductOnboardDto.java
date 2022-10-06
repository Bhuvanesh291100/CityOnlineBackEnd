package com.city.online.api.dto.request;

import com.city.online.api.model.enums.ProductType;
import com.city.online.api.model.enums.Status;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;
import java.util.List;

@Builder
@Data
public class ProductOnboardDto {

    String cuisineType;
    private String itemName;
    private String description;
    private Integer itemPrice;
    //Producttype is to accomodate both food and non food items, to support the ecommerce
    ProductType productType;
    boolean veg;
    Integer inventoryCount;
    //Saved in the cart
    Integer cartCount;
    //Saved in the user order table
    Integer orderedCount;
    String fileName;
    private List<String> foodImageUrls;
    //It will be the restaurantUser OR Ecommerce vendor
    private String vendorId;
    private String username;
    Status status;
}
