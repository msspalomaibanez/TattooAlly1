package com.example.prueba_tattooally.Models;

import android.graphics.Bitmap;

public class Publicacion {
    private int idPublicacion;
    private Bitmap foto;
    private String descripcion;
    private String localizacion;
    private String estilo;

    public Publicacion() {
    }

    public Publicacion(int idPublicacion, Bitmap foto, String descripcion, String localizacion,String estilo) {
        this.idPublicacion = idPublicacion;
        this.foto = foto;
        this.descripcion = descripcion;
        this.localizacion = localizacion;
        this.estilo = estilo;
    }

    public int getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    @Override
    public String toString() {
        return "Publicacion{" +
                "idPublicacion=" + idPublicacion +
                ", foto=" + foto +
                ", descripcion='" + descripcion + '\'' +
                ", localizacion='" + localizacion + '\'' +
                ", estilo='" + estilo + '\'' +
                '}';
    }
}
