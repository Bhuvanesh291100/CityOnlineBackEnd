package com.city.online.api.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemoveFromCartRequestDto {
    String username;
    String skuId;
    Integer cartTotalAmount;
}
