package com.example.prueba_tattooally;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.prueba_tattooally.databinding.FragmentChatBinding;
import com.example.prueba_tattooally.databinding.FragmentExplorarBinding;

import com.cometchat.pro.core.AppSettings;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
//import com.cometchat.pro.androiduikit.constants.AppConfig;
//import com.cometchat.pro.uikit.ui_components.calls.call_manager.listener.CometChatCallListener;
//import com.cometchat.pro.uikit.ui_settings.UIKitSettings;

/**
 * Clase en la que se mostrará un listado de usuarios con los que se ha tenido un intercambio de mensajes
 *
 * Funcionalidades:
 * - Ingresar en alguna de las conversaciones para visualizar el intercambio de mensajes
 * - Navegación a otras pantallas con el menú inferior
 */

public class ChatActivity extends Fragment {

    private FragmentChatBinding binding;
    private String appID = "2110668471160e0b"; // Replace with your App ID
    private String region = "eu"; // Replace with your App Region ("eu" or "us")
    private String authKey = "8fd27206fe305fa8d1d9c73ff5e564fe1efe9c57"; // Replace with your App Region ("eu" or "us")

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        inicioChat();
        return root;
    }


   private void inicioChat() {
       AppSettings appSettings=new AppSettings.AppSettingsBuilder().subscribePresenceForAllUsers().setRegion(region).build();

       CometChat.init(this.getContext(), appID,appSettings, new CometChat.CallbackListener<String>() {
           @Override
           public void onSuccess(String successMessage) {
//               Log.d(TAG, "Initialization completed successfully");
           }
           @Override
           public void onError(CometChatException e) {
//               Log.d(TAG, "Initialization failed with exception: " + e.getMessage());
           }
       });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
