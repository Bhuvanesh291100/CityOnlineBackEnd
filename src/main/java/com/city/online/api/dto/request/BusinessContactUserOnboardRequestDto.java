package com.city.online.api.dto.request;

import com.city.online.api.model.enums.Status;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;

@Data
@Builder
public class BusinessContactUserOnboardRequestDto {
    @NotBlank
    @NotNull
    private String username;

    @NotBlank
    @NotNull
    private String firstName;

    @NotBlank
    @NotNull
    private String lastName;

    @Email
    @NotBlank
    @NotNull
    private String emailId;

    @NotBlank
    @Min(value = 10, message = "Mobile number should be a 10 digit number")
    @Max(value = 10, message = "Mobile number should be a 10 digit number")
    private Long mobileNumber1;

    @Min(value = 10, message = "Mobile number should be a 10 digit number")
    @Max(value = 10, message = "Mobile number should be a 10 digit number")
    private Long mobileNumber2;

    @NotBlank
    @NotNull
    private String businessName;

    private String businessDescription;

    @NotBlank
    @NotNull
    private String streetName;

    @NotBlank
    @NotNull
    private String cityName;

    @NotBlank
    @NotNull
    private String stateName;

    @NotBlank
    @NotNull
    private String mainBusinessCategory;

    private String address;

    // optional fields
    private List<String> subBusinessCategoryList;
    private String startTime;
    private String endTime;
    private List<String> holiday;

    Status status;
}
