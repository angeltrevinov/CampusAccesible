package com.example.campusaccesible;

public class Detalles {
    private String strNombre;
    private String imgIconURL;
    private String imgCheckURL;

    // Constructor
    public Detalles(String strNombre, String imgIconURL, String imgCheckURL) {
        this.strNombre = strNombre;
        this.imgIconURL = imgIconURL;
        this.imgCheckURL = imgCheckURL;
    }


    // Get y Set de las variables
    public String getStrNombre() {
        return strNombre;
    }

    public void setStrNombre(String strNombre) {
        this.strNombre = strNombre;
    }

    public String getImgIconURL() {
        return imgIconURL;
    }

    public void setImgIconURL(String imgIconURL) {
        this.imgIconURL = imgIconURL;
    }

    public String getImgCheckURL() {
        return imgCheckURL;
    }

    public void setImgCheckURL(String imgCheckURL) {
        this.imgCheckURL = imgCheckURL;
    }
}
