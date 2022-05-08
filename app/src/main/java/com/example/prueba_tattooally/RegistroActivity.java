package com.example.prueba_tattooally;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Clase en la que se le pedirá al usuario sus datos de registro y que tendrá un botón de registro donde
 * se comprobará que ese usuario no existe previamente en la base de datos
 *
 * Funcionalidades:
 * - Dar paso al usuario a la pagina inicial de la app con la cuenta ingresada
 * - Retroceder a la primera pantalla de la app para escoger entre inicio de sesión o registro
 */
public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        getSupportActionBar().hide();
    }
}
