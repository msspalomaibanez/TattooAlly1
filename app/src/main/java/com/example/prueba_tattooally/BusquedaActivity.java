package com.example.prueba_tattooally;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.prueba_tattooally.databinding.FragmentBusquedaBinding;

/**
 * Clase en la que se mostrará un listado de los resultado a la búsqueda en el fragmento de
 * explorar anterior
 *
 * Funcionalidades:
 * - Mostrar resultados
 */

public class BusquedaActivity extends Fragment {

    private FragmentBusquedaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBusquedaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}