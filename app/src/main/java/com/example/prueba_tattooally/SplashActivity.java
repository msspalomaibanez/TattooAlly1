package com.example.prueba_tattooally;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prueba_tattooally.login.LoginActivity;


/**
 * Clase que aparecerá por defecto como primera pantalla donde se le pedirá al usuario que se registre o que inicie sesión
 * con dos botones
 *
 * Funcionalidades:
 * - Dirigirse a la actividad de inicio de seión
 * - Dirigirse a la actividad de registro
 */
public class SplashActivity extends AppCompatActivity {

    Button inicio;
    Button registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        getSupportActionBar().hide();

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
}