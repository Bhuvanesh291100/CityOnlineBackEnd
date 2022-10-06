package com.city.online.api.model;

import com.city.online.api.model.base.MongoAuditBaseEntity;
import com.city.online.api.model.pojo.Address;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
@Builder
public class User extends MongoAuditBaseEntity implements Serializable {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String emailId;
    private String password;
    private String phoneNumber;
    List<Address> addressList = new ArrayList<>();
    private Date dateOfBirth;
    private String role;

}
