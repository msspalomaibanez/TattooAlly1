package com.example.prueba_tattooally.perfil;

import static com.example.prueba_tattooally.utils.JSONArrayAPublicaciones;
import static com.example.prueba_tattooally.utils.JSONArrayAPublicacionesPerfil;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.prueba_tattooally.Models.Publicacion;
import com.example.prueba_tattooally.Models.Usuario;
import com.example.prueba_tattooally.R;
import com.example.prueba_tattooally.adapter.publicacionesInicioAdapter;
import com.example.prueba_tattooally.adapter.publicacionesPerfilAdapter;
import com.example.prueba_tattooally.databinding.FragmentPerfilBinding;
import com.example.prueba_tattooally.inicio.HomeFragment;
import com.example.prueba_tattooally.inicio.MainActivity;
import com.example.prueba_tattooally.utils;
import com.facebook.imagepipeline.common.SourceUriType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

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
    CircleImageView imagen_perfil;
    TextView nickname_perfil;
    TextView num_seguidores_txt;
    TextView num_seguidos_txt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        imagen_perfil = root.findViewById(R.id.perfil_img);
        nickname_perfil = root.findViewById(R.id.perfil_nickname_txt);
        num_seguidores_txt = root.findViewById(R.id.num_seguidores_txt);
        num_seguidos_txt = root.findViewById(R.id.num_seguidos_txt);

        editar_btn = (Button) root.findViewById(R.id.editar_perfil_btn);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        /*EMULADOR*/
        cargarPerfil("http://10.0.2.2/tattooally_php/mostrar_perfil.php");
        /*DISPOSITIVOS MOVILES*/
        //obtenerPublicaciones("http://192.168.1.138/tattooally_php/mostrar_perfil.php");

        ArrayList<Publicacion> publicaciones = HomeFragment.getPublicaciones();
        for(int x = 0; x < publicaciones.size();x++){
            if(publicaciones.get(x).getIdUsuario() != 1){
                publicaciones.remove(x);
            }
        }
        mostrarPublicacionesPerfil(publicaciones);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void cargarPerfil(String URL){
        JsonArrayRequest jsonArrayRequest  = new JsonArrayRequest( URL
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray arrayJSON = response;
                System.out.println(arrayJSON);
                try {
                    JSONObject objeto = arrayJSON.getJSONObject(0);
                    int idUsuario = objeto.getInt("idUsuario");
                    Bitmap imagenPerfil = utils.StringABitMap(objeto.getString("imagen"));
                    int seguidores = objeto.getInt("seguidores");
                    String nickname = objeto.getString("nickname");
                    Usuario nuevoUsuario = new Usuario(idUsuario,imagenPerfil,nickname,seguidores,1);

                    mostrarPerfil(nuevoUsuario);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getBaseContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                System.out.println(error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("idUsuario","1");
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }



    private void mostrarPublicacionesPerfil(ArrayList<Publicacion> publicaciones) {
        if(binding != null){
            View root = binding.getRoot();
            GridView gridView = root.findViewById(R.id.grid_publis_perfil);
            publicacionesPerfilAdapter publicacionesPerfilAdapter = new publicacionesPerfilAdapter(getContext(),publicaciones);
            gridView.setAdapter(publicacionesPerfilAdapter);
        }
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



    public void mostrarPerfil(Usuario u){
        imagen_perfil.setImageBitmap(u.getFotoPerfil());
        nickname_perfil.setText(u.getNickname());
        num_seguidores_txt.setText(String.valueOf(u.getSeguidores()) + "\n seguidores");
        num_seguidos_txt.setText(String.valueOf(u.getSiguiendo() + "\n siguiendo"));

    }
}
