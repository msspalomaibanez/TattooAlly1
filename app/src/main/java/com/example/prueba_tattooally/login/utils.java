package com.example.prueba_tattooally.login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class utils {
    public static String convertirContrasena(String contrasena){
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        byte[] hash = md.digest(contrasena.getBytes());
        StringBuilder sb = new StringBuilder();

        for(byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static Connection  conectarDB() throws LoginException {
        Connection conexion = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
             conexion = DriverManager.getConnection("jdbc:mysql://192.168.1.128:3306/tattooally","tattoally","123abc.");

            return conexion;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new LoginException("Se ha producido un fallo en la conexión con la base de datos.");
        }
        return conexion;
    }
}
