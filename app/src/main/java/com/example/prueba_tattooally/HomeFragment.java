package com.example.prueba_tattooally;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.prueba_tattooally.R;
import com.example.prueba_tattooally.databinding.FragmentHomeBinding;

/**
 * Clase en la que se mostrarán las publicaciones de otros usuarios de la app en formato de listado y desde donde
 * habrá un menú inferior con la navegación a todas las diferentes pantallas de la app
 *
 * Funcionalidades:
 * - Visualización de publicaciones
 * - Navegación a otras pantallas con el menú inferior
 */

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}