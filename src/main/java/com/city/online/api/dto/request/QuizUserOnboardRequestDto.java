package com.city.online.api.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;



@Data
@Builder
public class QuizUserOnboardRequestDto {
    //private String userId;
    private String username;
    private String quizId;
    private String quizName;
    private String quizDate;
    private String firstName;
    private String lastName;
    private String parentFirstName;
    private String parentLastName;
    private Long mobileNumber;
    private Long parentMobileNumber;
    private String schoolName;
    private String qualification;
    private String address;
    private String city;
    private String pinCode;
    private String emailId;
}
