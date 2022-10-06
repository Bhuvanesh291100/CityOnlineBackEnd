package com.city.online.api.model.enums;

public enum AdType {
    CAROUSEL("Carousel Type"),
    PRIORITY_1("First priority Type"),
    SELF_ADVERTISEMENT("Self Advertisement");

    private String description;

    AdType(String description) {
        this.description = description;
    }
}
