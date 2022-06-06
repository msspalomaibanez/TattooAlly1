package com.example.prueba_tattooally.explorar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.prueba_tattooally.Models.Publicacion;
import com.example.prueba_tattooally.R;
import com.example.prueba_tattooally.adapter.publicacionesInicioAdapter;
import com.example.prueba_tattooally.databinding.FragmentBusquedaBinding;

import java.util.ArrayList;

/**
 * Clase en la que se mostrará un listado de los resultado a la búsqueda en el fragmento de
 * explorar anterior
 *
 * Funcionalidades:
 * - Mostrar resultados
 */

public class BusquedaActivity extends Fragment {

    private FragmentBusquedaBinding binding;
    ArrayList<Publicacion> publicacionesFiltradas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBusquedaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mostrarPublicaciones(ExplorarActivity.publicacionesFiltradas);


        return root;
    }

    public void mostrarPublicaciones(ArrayList<Publicacion> publicaciones){
        if(binding != null){
            View root = binding.getRoot();
            GridView gridView = root.findViewById(R.id.grid_resultados_filtrado);
            publicacionesInicioAdapter publicacionesInicioAdapter = new publicacionesInicioAdapter(getContext(), publicaciones);
            gridView.setAdapter(publicacionesInicioAdapter);
        }


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}