package com.city.online.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OptionDto {
    private String optionName;
    private String optionValue;
}
