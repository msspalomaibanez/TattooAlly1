package com.example.prueba_tattooally.registro;

import static com.example.prueba_tattooally.utils.*;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.prueba_tattooally.inicio.MainActivity;
import com.example.prueba_tattooally.R;
import com.example.prueba_tattooally.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase en la que se le pedirá al usuario sus datos de registro y que tendrá un botón de registro donde
 * se comprobará que ese usuario no existe previamente en la base de datos
 *
 * Funcionalidades:
 * - Dar paso al usuario a la pagina inicial de la app con la cuenta ingresada
 * - Retroceder a la primera pantalla de la app para escoger entre inicio de sesión o registro
 */
public class RegistroActivity extends AppCompatActivity {
    EditText nombre;
    EditText nickname;
    EditText email;
    EditText contrasena1;
    EditText contrasena2;
    Button registro;
    View atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        //escondemos la barra superior de la interfaz
        getSupportActionBar().hide();

        nombre = findViewById(R.id.loginNickname);
        nickname = findViewById(R.id.nick_edittxt);
        email = findViewById(R.id.email_edittxt);
        contrasena1 = findViewById(R.id.pass_edittxt);
        contrasena2 = findViewById(R.id.pass_edittxt_2);
        registro = findViewById(R.id.registro_btn);
        atras = findViewById(R.id.back);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //recogemos dentro de la variable aux el resultado de las validaciones
                boolean aux = validarCampos();
                //en caso de que las validaciones se hayan completado sin ningún problema se ejecutarán
                //los métodos de registro y del diálogo de carga
                if (aux) {
                    registrarUsuario("http://10.0.2.2/tattooally_php/registrar_usuario.php");
                    dialogoCarga();
                }
            }
        });
        //añadimos la funcionalidad de volver a la anterior actividad al puplsar en la flecha añadida en la interfaz
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * Método por el cual se lleva a cabo el registro de un usuario a la base de datos. Tras comprobar la validez de
     * los valores introducidos, hacemos una petición a la base de datos: en caso de que no haya errores, se lanzará la
     * actividad de inicio, en caso de haber errores se avisará al usuario del error y se almacenará dicho error en un
     * archivo log
     * @param URL en la que se encuentra el archivo php con el registro de los datos a la base de datos
     */
    public void registrarUsuario(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals(1)){ //AÑADIR VERIFICACIÓN VALIDACIONES AQUÍ
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                }else{
                    Toast.makeText(RegistroActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(RegistroActivity.this, "Error, enviando datos del error...", Toast.LENGTH_SHORT).show();
   /*V.Desarrollo*/ System.out.println(error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String,String>();
                parametros.put("nombre",nombre.getText().toString());
                parametros.put("nickname",nickname.getText().toString());
                parametros.put("email",email.getText().toString());
                parametros.put("contrasena",convertirContrasena(contrasena1.getText().toString()));
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    /**
     * Método por el cual ejecutaremos un diálogo emergente con la información correspondiente a los requisitos
     * de los campos del formulario
     */
    public void dialogoAyuda() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistroActivity.this);
        builder.setTitle(R.string.alert_registro);
        builder.setMessage(R.string.msg_registro);
        builder.setCancelable(false);

        builder.setPositiveButton(R.string.opcion_aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogo1.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Método por el cual ejecutaremos un diálogo emergente como pantalla de carga para mostrarle al usuario que
     * se está ejecutando una petición a la base de datos
     */
    public void dialogoCarga() {
        ProgressDialog dialog = new ProgressDialog(RegistroActivity.this);
        if (!isFinishing()){
            dialog.show(RegistroActivity.this, "", "Cargando...", true);
        }
    }

    /**
     * Método en el cual recogemos los valores de los campos del formulario y comprobamos que cumplan los requisitos
     * @return aux booleano que devolverá true en caso de que se cumplan los requisitos y false en caso de que no
     */
    public boolean validarCampos() {
        boolean aux = true;
        String nom = nombre.getText().toString();
        String nick = nickname.getText().toString();
        String em = email.getText().toString();
        String pass = contrasena1.getText().toString();
        if(!(utils.validarNombre(nom) && utils.validarNickname(nick) && utils.validarEmail(em) && utils.validarPassword(pass))) {
            return aux;
        } else {
            aux = false;
            dialogoAyuda();
        }
        return aux;
    }




}
