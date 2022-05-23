package com.example.prueba_tattooally;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import com.example.prueba_tattooally.databinding.FragmentExplorarBinding;

/**
 * Clase en la que se mostrará una barra de búsqueda para buscar usuarios o categorías seguido de un listado
 * de categorias frecuentes
 *
 * Funcionalidades:
 * - Buscar usuarios y categorias
 */

public class ExplorarActivity extends Fragment {

    private FragmentExplorarBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentExplorarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}