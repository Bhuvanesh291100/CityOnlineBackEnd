package com.city.online.api.dto.request;

import com.city.online.api.model.Product;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class AddToCartRequestDto {
    String username;
    Product cartItem;
    String restaurantUserId;
    Integer cartTotalAmount;
}
