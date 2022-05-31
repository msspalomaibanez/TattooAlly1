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
    public static boolean validarNombre (String nombre){
        boolean aux = true;
        //Si el nombre está vacío será falso
        if (nombre.isEmpty()) {
            aux = false;
        }
        //Si el nombre no está entre 3 y 12 caracteres será falso
        if (nombre.length() < 3 || nombre.length() > 12) {
            aux = false;
        }
        //Si el nombre contiene algún dígito o algún espacio será falso
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
    public static boolean validarNickname (String nickname) {
        boolean aux = true;
        //Si el nickname está vacío será falso
        if (nickname.isEmpty()) {
            aux = false;
        }
        //Si el nickname no está entre 3 y 12 caracteres será falso
        if (nickname.length() < 3 || nickname.length() > 12) {
            aux = false;
        }
        return aux;
    };
    /**
     * Método por el cual comprobamos la validez del campo email
     * @param email el nombre a comprobar
     * @return booleano que será true de ser válido o false de no serlo
     */

    public static boolean validarEmail (String email) {
        String pattern = "^(.+)@(\\S+)$";
        boolean aux = true;
        //Si el email está vacío será falso
        if(email.isEmpty()) {
            aux = false;
        }
        //Si el email no es igual al patrón que hemos establecido previamente será falso
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
    public static boolean validarPassword (String pass) {
        boolean aux = true;
        //Si la contraseña está vacía será falso
        if(pass.isEmpty()) {
            aux = false;
        }
        //Si la contraseña no tiene 8 caracteres será falso
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
