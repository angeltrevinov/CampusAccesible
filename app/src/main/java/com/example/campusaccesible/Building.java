package com.example.campusaccesible;

/**
 * Class that holds the structure of our info
 * TODO: Change to attributes needed
 */
public class Building {

    private String strName;
    public String imgUrl; //For when we receive different images

    // -----------------------------------------------------
    public Building(
            String strName,
            String imgUrl
    ) {
        this.strName = strName;
        this.imgUrl = imgUrl;
    }

    // -----------------------------------------------------
    public String getStrName() {
        return strName;
    }

    // -----------------------------------------------------
    public void setStrName(String strName) {
        this.strName = strName;
    }

    // -----------------------------------------------------
    public String getImgUrl() {
        return imgUrl;
    }
    // -----------------------------------------------------
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
