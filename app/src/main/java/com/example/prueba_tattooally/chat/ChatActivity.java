package com.example.prueba_tattooally.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.prueba_tattooally.R;
import com.example.prueba_tattooally.databinding.FragmentChatBinding;


/**
 * Clase en la que se mostrará un listado de usuarios con los que se ha tenido un intercambio de mensajes
 *
 * Funcionalidades:
 * - Ingresar en alguna de las conversaciones para visualizar el intercambio de mensajes
 * - Navegación a otras pantallas con el menú inferior
 */

public class ChatActivity extends Fragment {

    private FragmentChatBinding binding;
    ImageView img;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        img = root.findViewById(R.id.img_chat);
        img.setImageResource(R.drawable.error);
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
