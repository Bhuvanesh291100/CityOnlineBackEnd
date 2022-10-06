package com.city.online.api.model;

import com.city.online.api.model.base.MongoAuditBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cart")
public class Cart extends MongoAuditBaseEntity implements Serializable {
    @Id
    String id;

    String username;
    List<Product> cartItems = new ArrayList<>();
    Integer cartTotalAmount;
}
