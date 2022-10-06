package com.city.online.api.model;

import com.city.online.api.model.base.MongoAuditBaseEntity;
import com.city.online.api.model.enums.RestaurantWorkingStatus;
import com.city.online.api.model.enums.Status;
import com.city.online.api.model.pojo.AccountDetails;
import com.city.online.api.model.pojo.PaymentType;
import com.city.online.api.model.pojo.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;

/*
* This entity makes username + restaurant as a unique combination
* One user can have more than one restaurants registered
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "restaurantUser")
@Builder
public class RestaurantUser extends MongoAuditBaseEntity implements Serializable {
    //Restaurant user unique ID
    @Id
    String id;

    String username;
    String firstName;
    String lastName;
    Long ownerMobileNumber;
    String ownerEmailId;
    String managerName;
    Long managerMobileNumber;
    String managerEmailId;
    Restaurant restaurantDetails;
    AccountDetails accountDetails;
    RestaurantWorkingStatus restaurantWorkingStatus;
    Status status;
}
