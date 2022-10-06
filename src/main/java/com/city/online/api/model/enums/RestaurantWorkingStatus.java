package com.city.online.api.model.enums;

public enum RestaurantWorkingStatus {
    RESTAURANT_OPEN("Restaurant is open to accept food orders"),
    RESTAURANT_CLOSE("Restaurant is no longer accepting food orders"),
    RESTAURANT_INACTIVE("Restaurant is no longer Active"),
    RESTAURANT_ACTIVE("Restaurant is Active");

    private String description;

    RestaurantWorkingStatus(String description) {
        this.description = description;
    }
}

