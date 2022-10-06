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
 * This entity makes username + ecommerceBusiness as a unique combination
 * One user can have more than one restaurants registered
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "eCommerceUser")
@Builder
public class EcommerceUser extends MongoAuditBaseEntity implements Serializable {
    //Vendor Id
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
    String restaurantId;
    Restaurant restaurantDetails;
    AccountDetails accountDetails;
    PaymentType paymentModesAvailable;
    RestaurantWorkingStatus restaurantWorkingStatus;
    Status status;
}
