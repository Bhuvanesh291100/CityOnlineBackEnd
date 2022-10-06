package com.city.online.api.model.enums;

public enum Qualification {
    PRIMARY_SCHOOL("Primary School From 2rd to 4th Standard"),
    SECONDARY_SCHOOL("Secondary School from 5th to 10th Standard"),
    JUNIOR_COLLEGE("11th and 12th"),
    GRADUATION("Undergraduate and Graduate"),
    POST_GRADUATE("Post Graduation");

    private String description;

    private Qualification(String description){
        this.description = description;
    }
    public String getdescription(){
        return description;
    }
}
