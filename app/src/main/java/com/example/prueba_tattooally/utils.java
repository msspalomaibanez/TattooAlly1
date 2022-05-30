package com.example.prueba_tattooally;

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
    };

    /**
     * Método por el cual comprobamos la validez del campo nombre
     * @param nombre el nombre a comprobar
     * @return booleano que será true de ser válido o false de no serlo
     */
    public boolean validarNombre (String nombre){
        boolean aux = true;
        if (nombre.isEmpty()) {
            aux = false;
        }
        if (nombre.length() < 1 || nombre.length() > 150) {
            aux = false;
        }
        for (char c : nombre.toCharArray()) {
            if (Character.isDigit(c) || (!Character.isLetter(c) && c != ' ')) {
                aux = false;
                break;
            }
        }
        return aux;
    };

    /**
     * Método por el cual comprobamos la validez del campo nickname
     * @param nickname el nombre a comprobar
     * @return booleano que será true de ser válido o false de no serlo
     */
    public boolean validarNickname (String nickname) {
        boolean aux = true;
        if (nickname.isEmpty()) {
            aux = false;
        }
        if (nickname.length() < 1 || nickname.length() > 150) {
            aux = false;
        }
        return aux;
    };
    /**
     * Método por el cual comprobamos la validez del campo email
     * @param email el nombre a comprobar
     * @return booleano que será true de ser válido o false de no serlo
     */

    public boolean validarEmail (String email) {
        String pattern = "^(.+)@(\\S+)$";
        boolean aux = true;
        if(email.isEmpty()) {
            aux = false;
        }

        if (email != pattern) {
            aux = false;
        }
        return aux;
    };

    /**
     * Método por el cual comprobamos la validez del campo contraseña
     * @param pass la contraseña a comprobar
     * @return booleano que será true de ser válido o false de no serlo
     */
    public boolean validarPassword (String pass) {
        boolean aux = true;
        //Si la contraseña está vacía será falso
        if(pass.isEmpty()) {
            aux = false;
        }
        //Si la contraseá no tiene 8 caracteres será falso
        if (pass.length() != 8) {
            aux = false;
        }
        //Si la contraseña no tiene un dígito o contiene algún espacio será falso
        for (char c : pass.toCharArray()) {
            if (Character.isLetter(c) ||  c == ' ') {
                aux = false;
                break;
            }

        }
        return aux;
    }
}
