package com.city.online.api.model;
import com.city.online.api.model.base.MongoAuditBaseEntity;
import com.city.online.api.model.enums.Status;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "businessContactUser")
@Builder
public class BusinessContactUser extends MongoAuditBaseEntity implements Serializable {

    @Id //default id will be generated
    private String id;

    private String username;
    private String firstName;
    private String lastName;
    private String emailId;
    private Long mobileNumber1;
    private Long mobileNumber2;
    private String businessName;
    private String businessDescription;
    private String address;
    private String streetName;
    private String cityName;
    private String stateName;
    private String mainBusinessCategory;
    public List<String> subBusinessCategoryList;

    // optional fields
    private String startTime;
    private String endTime;
    private List<String> holiday;

    //default
    private Status status = Status.UNDER_REVIEW;
}
