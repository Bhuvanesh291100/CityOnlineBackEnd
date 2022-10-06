package com.city.online.api.dto.request;

import com.city.online.api.util.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserCreateRequestDto {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String role;
    private String phoneNumber;
    private String emailId;
    //TODO- Change this to the date data type
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date dateOfBirth;
    private String address;
    private String city;
    private String pinCode;
}