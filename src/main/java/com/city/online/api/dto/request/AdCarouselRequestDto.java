package com.city.online.api.dto.request;

import com.city.online.api.model.enums.AdType;
import com.city.online.api.model.enums.Status;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@Builder
public class AdCarouselRequestDto {

    @NotBlank
    @NotNull
    private String firstName;

    @NotBlank
    @NotNull
    private String lastName;

    @NotBlank
    @NotNull
    private String url;

    @NotBlank
    @NotNull
    private String description;

    @NotBlank
    @Min(value = 10, message = "Mobile number should be a 10 digit number")
    @Max(value = 10, message = "Mobile number should be a 10 digit number")
    private Long mobileNumber;

    @Email
    @NotBlank
    @NotNull
    private String emailId;

    @NotBlank
    @NotNull
    private AdType advertisementType;

    Status status;
}
