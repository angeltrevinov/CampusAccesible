package com.example.campusaccesible;

public class Asistencia {

    private String Titulo;
    private String Descripcion;
    private String imgUrl;

    public Asistencia(String titulo, String descripcion, String imgUrl) {
        Titulo = titulo;
        Descripcion = descripcion;
        this.imgUrl = imgUrl;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
