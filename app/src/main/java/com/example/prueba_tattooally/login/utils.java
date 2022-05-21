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

        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conexion = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/tattooally?serverTimezone=UTC","tattooally","123abc.");

            return conexion;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e){
            System.out.println(e.toString());
            throw new LoginException(e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
