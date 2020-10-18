package com.example.campusaccesible;

public class Building {

    private String strName;
    //public String imgUrl; For when we receive different images

    // -----------------------------------------------------
    public Building(String strName) {
        this.strName = strName;
    }

    // -----------------------------------------------------
    public String getStrName() {
        return strName;
    }

    // -----------------------------------------------------
    public void setStrName(String strName) {
        this.strName = strName;
    }
}
