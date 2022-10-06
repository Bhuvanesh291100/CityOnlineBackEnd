package com.city.online.api.model.enums;

public enum Status {
    UNDER_REVIEW("User details under review"),
    OPEN_REGISTRATION("Open for registration"),
    ACTIVE("User has been activated"),
    REJECTED("User Rejected"),
    INACTIVE("User Inactive"),


    SOFT_DELETE(""),
    OUT_OF_STOCK("Out of stock");
    private String description;

    Status(String description) {
        this.description = description;
    }
}
