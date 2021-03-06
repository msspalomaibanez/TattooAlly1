package com.example.prueba_tattooally;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.JsonToken;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.prueba_tattooally.Models.MiSingleton;
import com.example.prueba_tattooally.Models.Publicacion;
import com.example.prueba_tattooally.Models.Usuario;
import com.example.prueba_tattooally.inicio.MainActivity;
import com.example.prueba_tattooally.login.SplashActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class utils {
    public static String convertirContrasena(String contrasena) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        byte[] hash = md.digest(contrasena.getBytes());
        StringBuilder sb = new StringBuilder();

        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    ;

    /**
     * Método por el cual comprobamos la validez del campo nombre
     *
     * @param nombre el nombre a comprobar
     * @return booleano que será true de ser válido o false de no serlo
     */
    public static boolean validarNombre(String nombre) {
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
    }

    ;

    /**
     * Método por el cual comprobamos la validez del campo nickname
     *
     * @param nickname el nombre a comprobar
     * @return booleano que será true de ser válido o false de no serlo
     */
    public static boolean validarNickname(String nickname) {
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
    }

    ;

    /**
     * Método por el cual comprobamos la validez del campo email
     *
     * @param email el nombre a comprobar
     * @return booleano que será true de ser válido o false de no serlo
     */

    public static boolean validarEmail(String email) {
        String pattern = "[A-Za-z0-9+_.-]+@(.+)$";
        boolean aux = true;
        //Si el email está vacío será falso
        if (email.isEmpty()) {
            aux = false;
        }
        Pattern patt = Pattern.compile(pattern);
        //Si el email no es igual al patrón que hemos establecido previamente será falso
        Matcher matcher = patt.matcher(email);
        if (!matcher.matches()) {
            aux = false;
        }


        return aux;
    }

    ;

    /**
     * Método por el cual comprobamos la validez del campo contraseña
     *
     * @param pass la contraseña a comprobar
     * @return booleano que será true de ser válido o false de no serlo
     */
    public static boolean validarPassword(String pass) {
        boolean aux = true;
        //Si la contraseña está vacía será falso
        if (pass.isEmpty()) {
            aux = false;
        }
        //Si la contraseña no tiene 8 caracteres será falso
        if (pass.length() != 8) {
            aux = false;
        }
        //Si la contraseña no tiene un dígito o contiene algún espacio será falso
        for (char c : pass.toCharArray()) {
            if (Character.isLetter(c) || c == ' ') {
                aux = false;
                break;
            }

        }
        return aux;
    }


    /**
     * Método por el cual convertimos un objeto de tipo Bitmap a String
     *
     * @param bitmap el objeto de tipo bitmap a parsear a String en base64
     * @return objeto de tipo String
     */
    public static String BitMapAString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imagen = Base64.encodeToString(b, Base64.DEFAULT);
        return imagen;
    }

    /**
     * Método por el cual convertimos un String de tipo encoded base64 a Bitmap
     *
     * @param encodedString el objeto de tipo String  que será casteado a un objeto de tipo Bitmap
     * @return objeto Bitmap
     */
    public static Bitmap StringABitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    /**
     * Método por el cual recibimos un JSONArray, recorremos este JSONArray y devolvemos sus objetos
     * mediante un ArrayList de publicaciones
     *
     * @param jsonArray El array JSON a recorrer
     * @return Devuelve un ArrayList de publicaciones
     */
    public static ArrayList<Publicacion> JSONArrayAPublicaciones(JSONArray jsonArray) {
        ArrayList<Publicacion> publicaciones = new ArrayList<Publicacion>();

        for (int x = 0; x < jsonArray.length(); x++) {

            try {
                JSONObject objeto = jsonArray.getJSONObject(x);
                int id = objeto.getInt("id");
                int idUsuario = objeto.getInt("idUsuario");
                String imagenAux = objeto.getString("imagen");
                String imagen  = imagenAux.replace("localhost",MainActivity.getIp().toString());
                String imagenPerfilAux = objeto.getString("fotoPerfil");
                String imagenPerfil  = imagenPerfilAux.replace("localhost",MainActivity.getIp().toString());

                String descripcion = objeto.getString("descripcion");
                String nickname = objeto.getString("nickname");
                String localizacion = objeto.getString("localizacion");
                String estilo = objeto.getString("estilo");

                Publicacion nuevaPublicacion = new Publicacion(id, idUsuario, nickname, imagen, imagenPerfil, descripcion, localizacion, estilo);
                publicaciones.add(nuevaPublicacion);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return publicaciones;

    }


    public static ArrayList<Publicacion> JSONArrayAPublicacionesPerfil(JSONArray jsonArray) {
        ArrayList<Publicacion> publicaciones = new ArrayList<Publicacion>();

        for (int x = 0; x < jsonArray.length(); x++) {

            try {
                JSONObject objeto = jsonArray.getJSONObject(x);
                int id = objeto.getInt("id");

                String imagen = objeto.getString("imagen");


                Publicacion nuevaPublicacion = new Publicacion();
                nuevaPublicacion.setIdPublicacion(id);
                nuevaPublicacion.setFotoPerfil(imagen);
                publicaciones.add(nuevaPublicacion);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return publicaciones;

    }

    public static void cargarUsuario(String URL, Context context) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray arrayJSON = response;
                try {
                    JSONObject objeto = arrayJSON.getJSONObject(0);
                    guardarUsuario(objeto);
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "No se ha podido recuperar el usuario!", Toast.LENGTH_SHORT).show();
                System.out.println(error.getMessage());

            }
        });

        MiSingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

    }

    private static Usuario guardarUsuario(JSONObject objeto) {
        Usuario usuarioLogeado = new Usuario();
        try {
            int idUsuario = objeto.getInt("idUsuario");
            String imagenPerfil = objeto.getString("imagen");
            String email = objeto.getString("email");
            String nombre = objeto.getString("nombre");
            int seguidores = objeto.getInt("seguidores");
            String nickname = objeto.getString("nickname");
            usuarioLogeado.setIdUsuario(idUsuario);
            usuarioLogeado.setFotoPerfil(imagenPerfil);
            usuarioLogeado.setEmail(email);
            usuarioLogeado.setNombre(nombre);
            usuarioLogeado.setNickname(nickname);
            usuarioLogeado.setSeguidores(seguidores);
            usuarioLogeado.setSiguiendo(0);
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        return usuarioLogeado;

    }

    public static ArrayList<Publicacion> copiarArrayPublicaciones(ArrayList<Publicacion> publicaciones) {
        ArrayList<Publicacion> nuevoArray = new ArrayList<Publicacion>();

        for (int i = 0; i < publicaciones.size(); i++) {
            Publicacion nuevaPublicacion = new Publicacion(publicaciones.get(i));
            nuevoArray.add(nuevaPublicacion);

        }
        return nuevoArray;
    }



}
