package com.example.prueba_tattooally;

import android.os.Bundle;
import android.widget.ImageView;

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
    ImageView imagen1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imagen1 = findViewById(R.id.img_publi_1);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_inicio, R.id.navigation_explorar, R.id.navigation_nuevo, R.id.navigation_chat, R.id.navigation_perfil)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        imagen1.setImageResource(R.drawable.tattoo);
        imagen1 = findViewById(R.id.img_publi_2);
        imagen1.setImageResource(R.drawable.tattooally);
    }

    //Sobreescribimos el siguiente método para que el usuario, una vez haya completado el ingreso tanto por login como por registro,
    //no pueda volver a la pantalla anterior
    @Override
    public void onBackPressed() {

    }
}