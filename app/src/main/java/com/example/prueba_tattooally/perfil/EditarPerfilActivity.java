package com.example.prueba_tattooally.perfil;

import static com.example.prueba_tattooally.utils.BitMapAString;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.prueba_tattooally.Models.MiSingleton;
import com.example.prueba_tattooally.Models.Usuario;
import com.example.prueba_tattooally.R;
import com.example.prueba_tattooally.databinding.FragmentEditarPerfilBinding;
import com.example.prueba_tattooally.inicio.MainActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditarPerfilActivity extends Fragment {

    private FragmentEditarPerfilBinding binding;
    ActivityResultLauncher<Intent> miActivityResultLauncher;
    private RequestQueue requestQueue;
    private Usuario usuario;
    private EditText nombre;
    private EditText nickname;
    private EditText email;
    private ImageView img_preview;
    private Bitmap img;
    private Button btn_editar;
    private String URL;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        binding = FragmentEditarPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        URL = "http://"+ MainActivity.getIp()+"/tattooally_php/editar_perfil.php";
        usuario = PerfilActivity.perfil;
        nombre = root.findViewById(R.id.editar_nom_edittxt);
        nickname = root.findViewById(R.id.editar_nick_edittxt);
        email = root.findViewById(R.id.editar_email_edittxt);
        img_preview = root.findViewById(R.id.img_editar_perfil);
        requestQueue = MiSingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue();
        cargarDatos();

        miActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Uri imageUri = result.getData().getData();
                            try {
                                img = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                                img_preview.setImageBitmap(img);
                                img_preview.setVisibility(View.VISIBLE);
                                img_preview.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                                img_preview.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });

        img_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });
        btn_editar = root.findViewById(R.id.editar_btn);
        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarDatos(URL);
            }
        });

        return root;
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void cambiarDatos(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(),"Tu perfil se ha actualizado",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"No se ha podido actualizar tu perfil", Toast.LENGTH_SHORT).show();
                System.out.println(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String,String>();
                parametros.put("nombre", nombre.getText().toString());
                parametros.put("nickname", nickname.getText().toString());
                parametros.put("email", email.getText().toString());
                parametros.put("imagen", BitMapAString(img));
                return parametros;
            }
        };
        MiSingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    public void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        miActivityResultLauncher.launch(intent);

    }

    public void cargarDatos() {
        nombre.setText(usuario.getNombre());
        nickname.setText(usuario.getNickname());
        email.setText(usuario.getEmail());
        img = usuario.getFotoPerfil();
        img_preview.setImageBitmap(img);
    }
}
