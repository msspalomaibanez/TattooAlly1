package com.example.prueba_tattooally.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prueba_tattooally.Models.Usuario;
import com.example.prueba_tattooally.R;
import com.example.prueba_tattooally.login.LoginActivity;
import com.example.prueba_tattooally.registro.RegistroActivity;


/**
 * Clase que aparecerá por defecto como primera pantalla donde se le pedirá al usuario que se registre o que inicie sesión
 * con dos botones
 *
 * Funcionalidades:
 * - Dirigirse a la actividad de inicio de sesión
 * - Dirigirse a la actividad de registro
 */
public class SplashActivity extends AppCompatActivity {

    Button inicio;
    Button registro;
    static String ip;
    static Usuario usuarioLogeado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        getSupportActionBar().hide();
        //IP DEL SERVIDOR
        ip = "192.168.1.138";
        registro = findViewById(R.id.registro_btn);
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzarActivityRegistro(null);
            }
        });
        inicio = findViewById(R.id.login_btn);
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzarActivityLogin(null);
            }
        });
    }

    public void lanzarActivityLogin(View view) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    };

    public void lanzarActivityRegistro(View view) {
        Intent i = new Intent(this, RegistroActivity.class);
        startActivity(i);
    }

    public static String getIp() {
        return ip;
    }
}