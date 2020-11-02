package com.example.campusaccesible;

import java.io.Serializable;

/**
 * Class that holds the structure of our info
 * TODO: Change to attributes needed
 */
public class Building implements Serializable {

    private String strId;
    private String strName;
    private String strDescription;
    private String strLatitute; // check if its string
    private String strLongtitude; // check if its string
    private String imgUrl; //For when we receive different images
    private Boolean bBathrooms;
    private Boolean bElevators;
    private Boolean bRamps;

    // -----------------------------------------------------
    public Building(
            String strId,
            String strName,
            String strDescription,
            String strLatitute,
            String strLongtitude,
            String imgUrl,
            Boolean bBathrooms,
            Boolean bElevators,
            Boolean bRamps
    ) {
        this.strId = strId;
        this.strName = strName;
        this.strDescription = strDescription;
        this.strLatitute = strLatitute;
        this.strLongtitude = strLongtitude;
        this.imgUrl = imgUrl;
        this.bBathrooms = bBathrooms;
        this.bElevators = bElevators;
        this.bRamps = bRamps;
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

    public String getStrId() {
        return strId;
    }

    public void setStrId(String strId) {
        this.strId = strId;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public String getStrLatitute() {
        return strLatitute;
    }

    public void setStrLatitute(String strLatitute) {
        this.strLatitute = strLatitute;
    }

    public String getStrLongtitude() {
        return strLongtitude;
    }

    public void setStrLongtitude(String strLongtitude) {
        this.strLongtitude = strLongtitude;
    }

    public Boolean getbBathrooms() {
        return bBathrooms;
    }

    public void setbBathrooms(Boolean bBathrooms) {
        this.bBathrooms = bBathrooms;
    }

    public Boolean getbElevators() {
        return bElevators;
    }

    public void setbElevators(Boolean bElevators) {
        this.bElevators = bElevators;
    }

    public Boolean getbRamps() {
        return bRamps;
    }

    public void setbRamps(Boolean bRamps) {
        this.bRamps = bRamps;
    }
}
