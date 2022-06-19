package com.example.prueba_tattooally.Models;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Publicacion implements Serializable {
    private int idPublicacion;
    private int idUsuario;
    private String nickname;
    private String urlFoto;
    private String urlFotoPerfil;
    private String descripcion;
    private String localizacion;
    private String estilo;

    public Publicacion() {
    }

    public Publicacion(int idPublicacion, int idUsuario, String nickname, String urlFoto, String urlFotoPerfil, String descripcion, String localizacion, String estilo) {
        this.idPublicacion = idPublicacion;
        this.idUsuario = idUsuario;
        this.nickname = nickname;
        this.urlFoto = urlFoto;
        this.urlFotoPerfil = urlFotoPerfil;
        this.descripcion = descripcion;
        this.localizacion = localizacion;
        this.estilo = estilo;
    }

    public Publicacion(int idPublicacion, int idUsuario, String nickname, String foto, String descripcion, String localizacion, String estilo) {
        this.idPublicacion = idPublicacion;
        this.idUsuario = idUsuario;
        this.nickname = nickname;
        this.urlFoto = foto;
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

    public String getFoto() {
        return urlFoto;
    }

    public void setFoto(String foto) {
        this.urlFoto = foto;
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

    public String getFotoPerfil() {
        return urlFotoPerfil;
    }

    public void setFotoPerfil(String urlFotoPerfil) {
        this.urlFotoPerfil = urlFotoPerfil;
    }
    public Publicacion(Publicacion p){
        this.idUsuario = p.idUsuario;
        this.urlFotoPerfil = p.urlFotoPerfil;
        this.nickname = p.nickname;
        this.urlFoto = p.urlFoto;
        this.idPublicacion = p.idPublicacion;
        this.estilo = p.estilo;
        this.localizacion = p.localizacion;
        this.descripcion = p.descripcion;

    }
    @Override
    public String toString() {
        return "Publicacion{" +
                "idPublicacion=" + idPublicacion +
                ", idUsuario=" + idUsuario +
                ", foto=" + urlFoto +
                ", descripcion='" + descripcion + '\'' +
                ", localizacion='" + localizacion + '\'' +
                ", estilo='" + estilo + '\'' +
                '}';
    }
}
