package com.city.online.api.dto.request;

import com.city.online.api.model.enums.Status;
import com.city.online.api.model.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RestaurantFoodMenuCreateRequestDto {
    //VendorId sent in the Product is considered as the RestaurantUserId
    List<ProductOnboardDto> foodItems;
    String username;
    String restaurantUserId;
}
