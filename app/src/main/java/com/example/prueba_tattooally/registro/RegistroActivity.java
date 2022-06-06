package com.example.prueba_tattooally.registro;

import static com.example.prueba_tattooally.utils.*;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    ImageView img_info;
    View atras;
    View v;
    private ProgressDialog progress;
    private int progressStatus = 0;
    private long fileSize = 0;
    private Handler progressBarHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        //escondemos la barra superior de la interfaz
        getSupportActionBar().hide();
        //asociamos el view al layout correspondiente
        v = findViewById(R.id.layout_registro);

        nombre = findViewById(R.id.nombreApellidosNuevo);
        nickname = findViewById(R.id.nick_edittxt);
        email = findViewById(R.id.email_edittxt);
        contrasena1 = findViewById(R.id.pass_edittxt);
        contrasena2 = findViewById(R.id.pass_edittxt_2);
        registro = findViewById(R.id.registro_btn);
        img_info = findViewById(R.id.img_info);
        atras = findViewById(R.id.back);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //recogemos dentro de la variable aux el resultado de las validaciones
                boolean aux = validarCampos();
                //en caso de que las validaciones se hayan completado sin ningún problema se ejecutarán
                //los métodos de registro y del diálogo de carga
                if (aux) {
                    registrarUsuario("http://192.168.1.121/tattooally_php/registrar_usuario.php");
                    dialogoCarga(v);
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

        img_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoAyuda();
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
     * Método en el cual recogemos los valores de los campos del formulario y comprobamos que cumplan los requisitos
     * @return aux booleano que devolverá true en caso de que se cumplan los requisitos y false en caso de que no
     */
    public boolean validarCampos() {
        boolean aux = true;
        String nom = nombre.getText().toString();
        String nick = nickname.getText().toString();
        String em = email.getText().toString();
        String pass = contrasena1.getText().toString();
        if(utils.validarNombre(nom)) {
            System.out.println("nombre bien");
        } else {
            aux = false;
            Toast.makeText(RegistroActivity.this, "El nombre tendrá entre 3 y 12 letras sin números", Toast.LENGTH_LONG).show();
            nombre.setText("");
        }
        if(utils.validarNickname(nick)){
            System.out.println("nickname bien");
        } else {
            aux = false;
            Toast.makeText(RegistroActivity.this, "El nickname tendrá entre 3 y 12 letras", Toast.LENGTH_LONG).show();
            nickname.setText("");
        }
        if (utils.validarEmail(em)){
            System.out.println("email bien");
        } else {
            aux = false;
            Toast.makeText(RegistroActivity.this, "El correo electrónico deberá tener la siguiente estructura __@__.__", Toast.LENGTH_LONG).show();
            email.setText("");
        }
        if(utils.validarPassword(pass)) {
            System.out.println("password bien");
        } else {
            aux = false;
            Toast.makeText(RegistroActivity.this, "La contraseña deberá tener 8 dígitos sólo numérica", Toast.LENGTH_LONG).show();
            contrasena1.setText("");
            contrasena2.setText("");
        }
        if(!contrasena1.getText().toString().equals(contrasena2.getText().toString())) {
            aux = false;
            Toast.makeText(RegistroActivity.this, "Las contraseñas introducidas tienen que ser la misma", Toast.LENGTH_LONG).show();
            contrasena1.setText("");
            contrasena2.setText("");
        }
        return aux;
    }

    /**
     * Método por el cual ejecutaremos un diálogo emergente como pantalla de carga para mostrarle al usuario que
     * se está ejecutando una petición a la base de datos
     */
    public void dialogoCarga(View v) {
        //preparamos la creación del dialogo
        progress = new ProgressDialog(v.getContext());
        //no dejamos que el usuario pueda interactuar con el resto de la app hasta que acabe el dialogo
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progress.setMessage("Cargando datos...");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setProgress(0);
        progress.setMax(100);
        progress.show();

        //reseteamos el valor de progreso
        progressStatus = 0;

        //establecemos a cero la cantidad de carga del proceso
        fileSize = 0;
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    // llamamos al método que simulará el proceso de carga
                    progressStatus = simulacionCarga();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // actualizamos la barra de progreso
                    progressBarHandler.post(new Runnable() {
                        public void run() {
                            progress.setProgress(progressStatus);
                        }
                    });
                }
                // cuando la barra de progeso llegue a su fin, cargamos el perfil
                if (progressStatus >= 100) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // cerramos el dialogo de progreso
                    progress.dismiss();
                }
            }
        }).start();
        //permitimos al usuario que interactue de nuevo con el resto de la app
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    /**
     * Método por el cual simulamos el proceso de carga para la barra de progreso
     * @return int el valor en el que se encuentra la carga en x momento
     */
    public int simulacionCarga() {

        while (fileSize <= 1000000) {

            fileSize++;

            if (fileSize == 100000) {
                return 10;
            } else if (fileSize == 200000) {
                return 20;
            } else if (fileSize == 300000) {
                return 30;
            } else if (fileSize == 400000) {
                return 40;
            } else if (fileSize == 500000) {
                return 50;
            } else if (fileSize == 600000) {
                return 60;
            } else if (fileSize == 700000) {
                return 70;
            } else if (fileSize == 800000) {
                return 80;
            } else if (fileSize == 900000) {
                return 90;
            } else if (fileSize == 1000000) {
                return 100;
            }
        }
        return 100;
    }


}
