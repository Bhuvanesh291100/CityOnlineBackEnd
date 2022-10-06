package com.city.online.api.dto.request;

import com.city.online.api.model.Product;
import com.city.online.api.model.pojo.Address;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class CheckoutRequestDto {
    String username;
    String paymentMode;
    Integer orderTotalAmount;
    List<Address> addressList = new ArrayList<>();

    String deliveryAddress;
    Long deliveryMobileNumber;
}
