package com.city.online.api.model.pojo;

import com.city.online.api.model.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EcommerceBusiness {
    Integer eBusinessId;
    String eBusinessName;
    String cityName;
    String stateName;
    Long pinCode;
    String estimatedTimeOfArrival;
    List<Product> productList = new ArrayList<>();
    List<String> eBusinessImageUrls = new ArrayList<>();;
    List<String> operatingDays = new ArrayList<>();;
    List<String> tags = new ArrayList<>();
}
