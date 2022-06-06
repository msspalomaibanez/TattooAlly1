package com.example.prueba_tattooally.nuevo;


import static androidx.navigation.fragment.NavHostFragment.findNavController;
import static com.example.prueba_tattooally.utils.BitMapAString;


import android.app.Activity;

import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.prueba_tattooally.Models.MiSingleton;
import com.example.prueba_tattooally.R;
import com.example.prueba_tattooally.databinding.FragmentHomeBinding;
import com.example.prueba_tattooally.databinding.FragmentNuevoBinding;
import com.example.prueba_tattooally.inicio.HomeFragment;
import com.example.prueba_tattooally.inicio.MainActivity;
import com.example.prueba_tattooally.login.SplashActivity;
import com.example.prueba_tattooally.utils;
import com.google.android.material.navigation.NavigationView;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase en la que se mostrará un formulario para subir una publicación a la plataforma
 *
 * Funcionalidades:
 * - Subir una publicación a la plataforma
 */

public class NuevoActivity extends Fragment {

    private FragmentNuevoBinding binding;
    private ActivityResultLauncher<Intent> miActivityResultLauncher;
    private RequestQueue requestQueue;
    private Button anadirImagenBtn;
    private Bitmap imagen;
    private ImageView previewImagen;
    private EditText descripcionImagen;
    private Spinner localizacionNuevo;
    private Spinner estiloNuevo;
    private Button publicar;
    private View root;
    private String URL;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNuevoBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        anadirImagenBtn = root.findViewById(R.id.anadirImagenBtn);
        previewImagen = root.findViewById(R.id.previewImagen);
        descripcionImagen = root.findViewById(R.id.descripcionNuevo);
        localizacionNuevo = root.findViewById(R.id.desplegableProvincias);
        estiloNuevo = root.findViewById(R.id.desplegableEstilos);
        publicar = root.findViewById(R.id.btnPublicar);
        requestQueue = MiSingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue();
        URL = "http://"+ SplashActivity.getIp()+"/tattooally_php/crear_publicacion.php";
        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imagen != null && !descripcionImagen.getText().toString().isEmpty() && !localizacionNuevo.getSelectedItem().equals("") && !estiloNuevo.getSelectedItem().equals("")){
                    crearPublicacion(URL);
               }else{
                    Toast.makeText(getContext(), "Tienes que rellenar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                previewImagen.setVisibility(View.GONE);
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
                                imagen = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                                previewImagen.setImageBitmap(imagen);
                                previewImagen.setVisibility(View.VISIBLE);
                                previewImagen.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                                previewImagen.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
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
//        ProgressDialog dialog = new ProgressDialog(getContext());
//        dialog.show(getContext(), "", "Publicando...", false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equalsIgnoreCase("publicado")){
                    Toast.makeText(getContext(),"Publicación creada!",Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(((ViewGroup)getView().getParent()).getId(), new NuevoActivity());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }else{
                    Toast.makeText(getContext(),"No se ha podido publicar!",Toast.LENGTH_SHORT).show();
                    System.out.println(response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getBaseContext(),"No se ha podido publicar!",Toast.LENGTH_SHORT).show();
                System.out.println(error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String milisegundos = String.valueOf(System.currentTimeMillis());
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("usuario","aguadix");
                parametros.put("nombreArchivo",milisegundos);
                parametros.put("imagen",BitMapAString(imagen));
                parametros.put("descripcion",descripcionImagen.getText().toString());
                parametros.put("localizacion",localizacionNuevo.getSelectedItem().toString());
                parametros.put("estilo",estiloNuevo.getSelectedItem().toString());

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



}