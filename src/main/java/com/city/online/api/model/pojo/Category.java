package com.city.online.api.model.pojo;

import com.city.online.api.model.enums.Status;
import lombok.Data;

@Data
public class Category{
    private String categoryCode;
    private String categoryName;
    private String categoryDescription;
    private Status status;
}
