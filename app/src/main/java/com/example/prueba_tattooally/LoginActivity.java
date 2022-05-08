package com.example.prueba_tattooally;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getSupportActionBar().hide();

        inicio = findViewById(R.id.inicio_btn);
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzarInicio(null);
            }
        });
    }

    public void lanzarInicio(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    };
}