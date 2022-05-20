package com.example.prueba_tattooally.login;

import static com.example.prueba_tattooally.login.utils.conectarDB;
import static com.example.prueba_tattooally.login.utils.convertirContrasena;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prueba_tattooally.MainActivity;
import com.example.prueba_tattooally.R;
import com.example.prueba_tattooally.login.LoginException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


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
                lanzarInicio(null);
               // iniciarSesion(String.valueOf(usuario.getText()), String.valueOf(contrasena.getText()));
            }
        });
    }

    public void lanzarInicio(View view) {
        System.out.println(convertirContrasena(valor_contrasena));
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    };

    public void iniciarSesion(String usuario, String contrasena) {
        Connection conexion = null;
        Statement statement = null;
        String contrasenaCifrada = convertirContrasena(contrasena);
        String querySQL = "SELECT * FROM Usuario WHERE email = " + usuario; //+ " and contrasena = " + contrasena;
        try {
            conexion = conectarDB();
            ResultSet resultSet = statement.executeQuery(querySQL);
            statement = conexion.createStatement();

            if (resultSet.next()) {
                Toast.makeText(this, "Logeo correcto", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Logeo incorrecto", Toast.LENGTH_LONG).show();
            }
        } catch (LoginException e) {
            Toast.makeText(this, e.getMensajeError(), Toast.LENGTH_LONG).show();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}