package com.city.online.api.model;

import com.city.online.api.model.base.MongoAuditBaseEntity;
import com.city.online.api.model.enums.AdType;
import com.city.online.api.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "advertisement")
@Builder
public class AdvertisementCarousel extends MongoAuditBaseEntity implements Serializable {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String url;
    private String description;
    private Long mobileNumber;
    private String emailId;
    private AdType advertisementType;
    private Status status;
}
