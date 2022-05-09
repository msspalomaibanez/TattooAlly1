package com.example.prueba_tattooally;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Clase en la que se le pedirá al usuario sus datos de inicio de sesión (nickname o correo electrónico) y
 * que tendrá un botón de inicio de sesión donde se validarán los datos con la base de datos
 *
 * Funcionalidades:
 * - Dar paso al usuario a la pagina inicial de la app con la cuenta ingresada
 * - Retroceder a la primera pantalla de la app para escoger entre inicio de sesión o registro
 */
public class LoginActivity extends AppCompatActivity {

    Button inicio;
    EditText usuario;
    EditText contrasena;
    String valor_contrasena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getSupportActionBar().hide();

        inicio = findViewById(R.id.inicio_btn);
        usuario = findViewById(R.id.usuario_edittxt);
        contrasena = findViewById(R.id.nick_edittxt);
        valor_contrasena = String.valueOf(contrasena.getText());
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valor_contrasena = String.valueOf(contrasena.getText());
                lanzarInicio(null);
            }
        });
    }

    public void lanzarInicio(View view) {
        System.out.println(convertirContrasena(valor_contrasena));
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    };
    public String convertirContrasena(String contrasena){
        MessageDigest md = null;
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
    public boolean comprobarDatos(String usuario, String contrasena){
        System.out.println();
        return false;
    }
}