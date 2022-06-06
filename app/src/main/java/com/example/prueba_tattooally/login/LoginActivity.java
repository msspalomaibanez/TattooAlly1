package com.example.prueba_tattooally.login;

import static com.example.prueba_tattooally.utils.convertirContrasena;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.prueba_tattooally.Models.Usuario;
import com.example.prueba_tattooally.R;
import com.example.prueba_tattooally.inicio.MainActivity;
import com.example.prueba_tattooally.perfil.PerfilActivity;
import com.example.prueba_tattooally.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


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
    String URL;
    View atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //escondemos la barra superior de la interfaz
        getSupportActionBar().hide();

        inicio = findViewById(R.id.inicio_btn);
        usuario = findViewById(R.id.nicknameLogin);
        contrasena = findViewById(R.id.passLogin);


        atras = findViewById(R.id.back);


        URL = "http://"+SplashActivity.getIp()+"/tattooally_php/validar_usuario.php";
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarUsuario(URL);
            }
        });
        //añadimos la funcionalidad de volver a la anterior actividad al pulsar en la flecha añadida en la interfaz
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * Método por el cual se lleva a cabo la comprobación de los datos de un usuario ya existente en la base de datos.
     * Hacemos una petición a la base de datos: en caso de que los valores introducidos coincidan con los de la base de datos
     * se lanzará la actividad de inicio, en caso de haber errores se avisará al usuario del error y se almacenará
     * dicho error en un archivo log
     * @param URL en la que se encuentra el archivo php con el login de los datos
     */
    public void validarUsuario(String URL){
        StringRequest stringRequest  = new StringRequest(Request.Method.POST,URL
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Correcto")){
                    Toast.makeText(getApplicationContext(),"Inicio de sesion correcto",Toast.LENGTH_SHORT)
                            .show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("usuarioLogeado", usuario.getText().toString());
                    startActivity(intent);
                }else if (response.equals("Incorrecto")){
                    Toast.makeText(getApplicationContext(),"Datos inválidos",Toast.LENGTH_SHORT)
                            .show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error.getMessage());

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String,String>();
                parametros.put("usuario",usuario.getText().toString());
                parametros.put("password",convertirContrasena(contrasena.getText().toString()));
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

}