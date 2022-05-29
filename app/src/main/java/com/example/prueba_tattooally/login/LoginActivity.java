package com.example.prueba_tattooally.login;

import static com.example.prueba_tattooally.utils.convertirContrasena;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.prueba_tattooally.MainActivity;
import com.example.prueba_tattooally.R;

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
    String valor_contrasena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getSupportActionBar().hide();

        inicio = findViewById(R.id.inicio_btn);
        usuario = findViewById(R.id.nombre_edittxt);
        contrasena = findViewById(R.id.nick_edittxt);
        valor_contrasena = String.valueOf(contrasena.getText());
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                //validarUsuario("http://10.0.2.2/tattooally_php/validar_usuario.php");
                //dialogoCarga();
            }
        });
    }


    public void validarUsuario(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
            }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(LoginActivity.this, "Error, enviando datos del error...", Toast.LENGTH_SHORT).show();
/*V.Desarrollo*/ System.out.println(error.toString());
            }
            }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String,String>();
                parametros.put("usuario",usuario.getText().toString());
                parametros.put("password",convertirContrasena(contrasena.getText().toString()));
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void dialogoCarga() {
        ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        if (!isFinishing()){
            dialog.show(LoginActivity.this, "", "Cargando...", true);
        }

    }
}