package com.example.prueba_tattooally.login;

public class LoginException extends  Exception{
    String mensajeError;


    public LoginException(String mensaje){
        this.mensajeError = mensaje;
    }

    public String getMensajeError(){
        return this.mensajeError;
    }
}
