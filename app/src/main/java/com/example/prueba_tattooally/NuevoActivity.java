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
import com.example.prueba_tattooally.databinding.FragmentNuevoBinding;

/**
 * Clase en la que se mostrará un formulario para subir una publicación a la plataforma
 *
 * Funcionalidades:
 * - Subir unaq publicación a la plataforma
 */

public class NuevoActivity extends Fragment {

    private FragmentNuevoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNuevoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}