package com.city.online.api.model;

import com.city.online.api.model.base.MongoAuditBaseEntity;
import com.city.online.api.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
    @Document(collection = "order")
public class UserOrder extends MongoAuditBaseEntity implements Serializable {
    @Id
    String id;

    String username;
    List<Product> orderedItems;
    Integer orderTotalAmount;
    Integer taxIncludedAmount;
    String paymentMode;
    OrderStatus orderStatus;
    String address;

    String deliveryAddress;
    Long deliveryMobileNumber;
}
