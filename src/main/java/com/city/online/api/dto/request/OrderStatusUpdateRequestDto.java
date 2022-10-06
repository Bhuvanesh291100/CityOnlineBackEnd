package com.city.online.api.dto.request;

import com.city.online.api.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusUpdateRequestDto {
    String id;
    String checkoutId;
    OrderStatus updateStatus;
}
