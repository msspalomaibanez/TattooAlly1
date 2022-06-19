package com.example.prueba_tattooally.Models;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Usuario {

    private int idUsuario;
    private String nombre;
    private String nickname;
    private String fotoPerfil;
    private String email;
    private int seguidores;
    private int siguiendo;


    public Usuario() {

    }

    public Usuario(int idUsuario, String nombre, String nickname, String fotoPerfil, String email, int seguidores, int siguiendo) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.nickname = nickname;
        this.fotoPerfil = fotoPerfil;
        this.email = email;
        this.seguidores = seguidores;
        this.siguiendo = siguiendo;
    }

    public Usuario(int idUsuario, String fotoPerfil, String nickname, int seguidores, int siguiendo) {
        this.idUsuario = idUsuario;
        this.fotoPerfil = fotoPerfil;
        this.nickname = nickname;
        this.seguidores = seguidores;
        this.siguiendo = siguiendo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(int seguidores) {
        this.seguidores = seguidores;
    }

    public int getSiguiendo() {
        return siguiendo;
    }

    public void setSiguiendo(int siguiendo) {
        this.siguiendo = siguiendo;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", nickname='" + nickname + '\'' +
                ", fotoPerfil=" + fotoPerfil +
                ", email='" + email + '\'' +
                ", seguidores=" + seguidores +
                ", siguiendo=" + siguiendo +
                '}';
    }
}
