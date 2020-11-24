package com.example.campusaccesible;

import com.google.firebase.firestore.DocumentReference;

public class Asistencia {

    private String strId;
    private String strName;
    private String strDescription;
    private String asistUrl;
    private DocumentReference imgUrl;

    // -----------------------------------------------------
    public Asistencia() { }

    // -----------------------------------------------------
    public Asistencia(
            String strId,
            String strName,
            String strDescription,
            String asistUrl,
            DocumentReference imgUrl
    ) {
        this.strId = strId;
        this.strName = strName;
        this.strDescription = strDescription;
        this.asistUrl = asistUrl;
        this.imgUrl = imgUrl;
    }

    public String getStrId() {
        return strId;
    }

    public void setStrId(String strId) {
        this.strId = strId;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public DocumentReference getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(DocumentReference imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAsistUrl() {
        return asistUrl;
    }

    public void setAsistUrl(String asistUrl) {
        this.asistUrl = asistUrl;
    }
}
