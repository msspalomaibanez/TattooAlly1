package com.example.prueba_tattooally;

import static android.app.Activity.RESULT_OK;
import static com.example.prueba_tattooally.utils.convertirContrasena;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.prueba_tattooally.R;
import com.example.prueba_tattooally.databinding.FragmentNuevoBinding;
import com.example.prueba_tattooally.login.LoginActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase en la que se mostrará un formulario para subir una publicación a la plataforma
 *
 * Funcionalidades:
 * - Subir unaq publicación a la plataforma
 */

public class NuevoActivity extends Fragment {

    private FragmentNuevoBinding binding;
    ActivityResultLauncher<Intent> miActivityResultLauncher;
    Button anadirImagenBtn;
    Bitmap imagen;
    ImageView previewImagen;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNuevoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        anadirImagenBtn = root.findViewById(R.id.anadirImagenBtn);
        previewImagen = root.findViewById(R.id.previewImagen);
        View.OnClickListener subidaImagen = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarImagen();

            }
        };
        View.OnClickListener eliminarImagen = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anadirImagenBtn.setText("+");
                previewImagen.setImageResource(R.drawable.tattooally);
                anadirImagenBtn.setOnClickListener(subidaImagen);
            }
        };
        anadirImagenBtn.setOnClickListener(subidaImagen);



         miActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Uri imageUri = result.getData().getData();
                            try {
                                imagen = MediaStore.Images.Media.getBitmap(root.getContext().getContentResolver(), imageUri);
                                previewImagen.setImageBitmap(imagen);
                                anadirImagenBtn.setText("-");
                                anadirImagenBtn.setOnClickListener(eliminarImagen);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void crearPublicacion(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();

                return parametros;
            }
        };

    }


    public void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        miActivityResultLauncher.launch(intent);

    }

}