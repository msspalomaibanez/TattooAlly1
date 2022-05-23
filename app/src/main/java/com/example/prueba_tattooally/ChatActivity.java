package com.example.prueba_tattooally;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.prueba_tattooally.databinding.FragmentChatBinding;
import com.example.prueba_tattooally.databinding.FragmentExplorarBinding;

public class ChatActivity extends Fragment {

    private FragmentChatBinding binding;
    private String appID = "APP_ID";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //inicioChat();
        return root;
    }


   /* private void inicioChat() {
        CometChat.init(this, appID,appSettings, new CometChat.CallbackListener<String>() {
            @Override
            public void onSuccess(String successMessage) {
                UIKitSettings.setAuthKey(authKey);
                CometChat.setSource("ui-kit","android","java");
                Log.d(TAG, "Initialization completed successfully");
            }
            @Override
            public void onError(CometChatException e) {
                Log.d(TAG, "Initialization failed with exception: " + e.getMessage());
            }
        });
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
