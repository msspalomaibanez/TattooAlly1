package com.example.prueba_tattooally;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
    Button buscar_btn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentExplorarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        buscar_btn = (Button) root.findViewById(R.id.buscar_btn);
        buscar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new BusquedaActivity();
                cambioFragment(fragment);
            }
        });
        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Método por el cual haremos la transición del fragmento actual al fragmento que le metamos
     * por parámetro
     */
    public void cambioFragment(Fragment fragment){
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(((ViewGroup)getView().getParent()).getId(), fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}