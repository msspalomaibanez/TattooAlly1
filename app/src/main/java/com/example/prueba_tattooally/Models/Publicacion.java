package com.example.prueba_tattooally.Models;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Publicacion implements Serializable {
    private int idPublicacion;
    private int idUsuario;
    private String nickname;
    private Bitmap foto;
    private Bitmap fotoPerfil;
    private String descripcion;
    private String localizacion;
    private String estilo;

    public Publicacion() {
    }

    public Publicacion(int idPublicacion, int idUsuario, String nickname, Bitmap foto, Bitmap fotoPerfil, String descripcion, String localizacion, String estilo) {
        this.idPublicacion = idPublicacion;
        this.idUsuario = idUsuario;
        this.nickname = nickname;
        this.foto = foto;
        this.fotoPerfil = fotoPerfil;
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

    public  Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public  String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public  String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public  String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Bitmap getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(Bitmap fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    @Override
    public String toString() {
        return "Publicacion{" +
                "idPublicacion=" + idPublicacion +
                ", idUsuario=" + idUsuario +
                ", foto=" + foto +
                ", descripcion='" + descripcion + '\'' +
                ", localizacion='" + localizacion + '\'' +
                ", estilo='" + estilo + '\'' +
                '}';
    }
}
