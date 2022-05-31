package com.example.prueba_tattooally;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.prueba_tattooally.databinding.FragmentExplorarBinding;
import com.example.prueba_tattooally.databinding.FragmentPerfilBinding;

/**
 * Clase en la que se mostrará el perfil del usuario con información sobre sus publicaciones
 *
 * Funcionalidades:
 * - Visualización de publicaciones
 * - Editar información del perfil personal
 * - Eliminar publicaciones
 */

public class PerfilActivity extends Fragment {


    private FragmentPerfilBinding binding;
    Button editar_btn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        editar_btn = (Button) root.findViewById(R.id.editar_btn);
        editar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EditarPerfilActivity();
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
