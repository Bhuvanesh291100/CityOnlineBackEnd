package com.city.online.api.model.pojo;

import com.city.online.api.model.Product;
import com.city.online.api.model.enums.RestaurantWorkingStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Restaurant implements Serializable {
    String restaurantName;
    String cityName;
    String stateName;
    Long pinCode;
    //String estimatedTimeOfArrival;
    List<String> restaurantImageUrls = new ArrayList<>();
    List<String> operatingDays = new ArrayList<>();
    List<String> tags = new ArrayList<>();
}
