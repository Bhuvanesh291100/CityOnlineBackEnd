package com.city.online.api.dto.request;

import com.city.online.api.model.enums.RestaurantWorkingStatus;
import com.city.online.api.model.enums.Status;
import com.city.online.api.model.pojo.AccountDetails;
import com.city.online.api.model.pojo.PaymentType;
import com.city.online.api.model.pojo.Restaurant;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RestaurantOnboardRequestDto implements Serializable {
    String username;
    String firstName;
    String lastName;
    Long ownerMobileNumber;
    String ownerEmailId;

    // optional fields
    String managerName;
    Long managerMobileNumber;
    String managerEmailId;

    Restaurant restaurantDetails;
    RestaurantWorkingStatus restaurantWorkingStatus;
    AccountDetails accountDetails;
    Status status;
}
