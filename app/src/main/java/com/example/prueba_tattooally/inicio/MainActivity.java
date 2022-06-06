package com.example.prueba_tattooally.inicio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.cometchat.pro.core.AppSettings;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.example.prueba_tattooally.R;
import com.example.prueba_tattooally.login.SplashActivity;
import com.example.prueba_tattooally.registro.RegistroActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_inicio, R.id.navigation_explorar, R.id.navigation_nuevo, R.id.navigation_chat, R.id.navigation_perfil)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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

            case R.id.cerrar_sesion:
                Toast.makeText(this, "Se ha cerrado su sesión", Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return true;
    }


}