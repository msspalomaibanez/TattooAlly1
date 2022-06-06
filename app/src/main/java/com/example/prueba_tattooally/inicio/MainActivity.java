package com.example.prueba_tattooally.inicio;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.prueba_tattooally.Models.Usuario;
import com.example.prueba_tattooally.R;
import com.example.prueba_tattooally.login.SplashActivity;
import com.example.prueba_tattooally.perfil.PerfilActivity;
import com.example.prueba_tattooally.utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.prueba_tattooally.databinding.ActivityMainBinding;

/**
 * Clase en la que se gestionarán las funcionalidades o características comunes de las diferentes opciones
 * del menú de navegación
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static String ip;
    View v;
    private ProgressDialog progress;
    private int progressStatus = 0;
    private long fileSize = 0;
    private Handler progressBarHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //IP DEL SERVIDOR
        ip = "192.168.1.138";
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //asociamos el view al layout correspondiente
        v = findViewById(R.id.layout_home);
        //ejecutamos el dialogo de carga en la creación del activity
        dialogoCarga(v);


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_inicio, R.id.navigation_explorar, R.id.navigation_nuevo, R.id.navigation_chat, R.id.navigation_perfil)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //cuando se pulse el elemento de cerrar sesión del menú superior aparecerá un toast con información
        navView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Se ha cerrado su sesión", Toast.LENGTH_SHORT)
                        .show();
            }
        });


    }

    //Sobreescribimos el siguiente método para que el usuario, una vez haya completado el ingreso tanto por login como por registro,
    //no pueda volver a la pantalla anterior
    @Override
    public void onBackPressed() {

    }

    /**
     * Método por el cual creamos un nuevo menú en la parte superior de la app
     * @param menu el cual vamos a asociar
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Método por el cual le damos funcionalidad a cada una de las opciones del menú superior
     * @param item el menú en sí
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //en el caso de seleccionar el modo oscuro, lanzamos un dialog con información
            case R.id.modo_oscuro:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.info_registro);
                builder.setMessage(R.string.msg_modo_oscuro);
                builder.setCancelable(false);

                builder.setPositiveButton(R.string.opcion_aceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            //en el caso de seleccionar la ayuda, lanzamos un dialog con información respectiva al funcionamiento de la app
            case R.id.ayuda:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setTitle(R.string.info_registro);
                builder1.setMessage(R.string.ayuda_txt);
                builder1.setCancelable(false);

                builder1.setPositiveButton(R.string.opcion_aceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.dismiss();
                    }
                });

                AlertDialog dialog1 = builder1.create();
                dialog1.show();
                break;
            //en el caso de seleccionar cerrar sesión, mandamos al usuario al inicio de la app
            case R.id.cerrar_sesion:
                Toast.makeText(this, "Se ha cerrado su sesión", Toast.LENGTH_SHORT)
                        .show();

                PerfilActivity.setPerfil(null);
                HomeFragment.setUsuarioLogeado(null);
                getIntent().putExtra("usuarioLogeado","");
                Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return true;
    }

    /**
     * Método por el cual obtenemos la ip
     * @return String con la ip
     */
    public static String getIp() {
        return ip;
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