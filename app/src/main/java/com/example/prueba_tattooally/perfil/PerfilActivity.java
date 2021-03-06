package com.example.prueba_tattooally.perfil;

import static com.example.prueba_tattooally.utils.JSONArrayAPublicaciones;
import static com.example.prueba_tattooally.utils.JSONArrayAPublicacionesPerfil;
import static com.example.prueba_tattooally.utils.copiarArrayPublicaciones;

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.prueba_tattooally.Models.MiSingleton;
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
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Clase en la que se mostrar?? el perfil del usuario con informaci??n sobre sus publicaciones
 *
 * Funcionalidades:
 * - Visualizaci??n de publicaciones
 * - Editar informaci??n del perfil personal
 * - Eliminar publicaciones
 */

public class PerfilActivity extends Fragment {


    private FragmentPerfilBinding binding;
    public static final String TAG = "solicitudPerfil";
    RequestQueue requestQueue;
    JsonArrayRequest jsonArrayRequest;
    Button editar_btn;
    CircleImageView imagen_perfil;
    TextView nickname_perfil;
    TextView num_seguidores_txt;
    TextView num_seguidos_txt;
    TextView num_publis_txt;
    static Usuario perfil;
    SwipeRefreshLayout gestoActualizar;
    ArrayList<Publicacion> publicacionesPerfil;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        imagen_perfil = root.findViewById(R.id.perfil_img);
        nickname_perfil = root.findViewById(R.id.perfil_nickname_txt);
        num_seguidores_txt = root.findViewById(R.id.num_seguidores_txt);
        num_seguidos_txt = root.findViewById(R.id.num_seguidos_txt);
        num_publis_txt = root.findViewById(R.id.num_publis_txt);

        requestQueue = MiSingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue();

        gestoActualizar = root.findViewById(R.id.gestoActualizarPerfil);
        gestoActualizar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarPerfil("http://"+MainActivity.getIp()+"/tattooally_php/cargar_perfil.php?nickname="+ HomeFragment.getUsuarioLogeado().getNickname());
            }
        });

        editar_btn = (Button) root.findViewById(R.id.editar_perfil_btn);
        editar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EditarPerfilActivity();
                cambioFragment(fragment);
            }
        });
        if(perfil == null){
            cargarPerfil("http://"+MainActivity.getIp()+"/tattooally_php/cargar_perfil.php?nickname="+HomeFragment.getUsuarioLogeado().getNickname());
        }else{

            publicacionesPerfil = copiarArrayPublicaciones(HomeFragment.getPublicaciones());
            publicacionesPerfil.removeIf(p -> p.getIdUsuario() != perfil.getIdUsuario());

            mostrarPerfil(perfil);
            mostrarPublicacionesPerfil(publicacionesPerfil);
        }




        return root;
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        if(requestQueue != null){
            requestQueue.cancelAll(TAG);
        }
    }

    private void cargarPerfil(String URL){
        jsonArrayRequest  = new JsonArrayRequest( URL
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray arrayJSON = response;
                try {
                    JSONObject objeto = arrayJSON.getJSONObject(0);
                    int idUsuario = objeto.getInt("idUsuario");
                    String imagenPerfilAux = objeto.getString("imagen");
                    String imagenPerfil = imagenPerfilAux.replace("localhost",MainActivity.getIp());
                    String email = objeto.getString("email");
                    String nombre = objeto.getString("nombre");
                    int seguidores = objeto.getInt("seguidores");
                    String nickname = objeto.getString("nickname");

                    perfil = new Usuario(idUsuario,nombre,nickname,imagenPerfil,email,seguidores,1);
                    publicacionesPerfil = copiarArrayPublicaciones(HomeFragment.getPublicaciones());

                    publicacionesPerfil.removeIf(p -> p.getIdUsuario() != perfil.getIdUsuario());
                    mostrarPublicacionesPerfil(publicacionesPerfil);
                    mostrarPerfil(perfil);
                    gestoActualizar.setRefreshing(false);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getBaseContext(),"No se ha podido mostrar el perfil actualizado!",Toast.LENGTH_SHORT).show();
                System.out.println(error.getMessage());
                gestoActualizar.setRefreshing(false);

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("idUsuario","1");
                return parametros;
            }
        };

        jsonArrayRequest.setTag(TAG);
        MiSingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
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
     * M??todo por el cual haremos la transici??n del fragmento actual al fragmento que le metamos
     * por par??metro
     */
    public void cambioFragment(Fragment fragment){
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(((ViewGroup)getView().getParent()).getId(), fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



    public void mostrarPerfil(Usuario u){
        Picasso.get()
                .load(u.getFotoPerfil())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(imagen_perfil);
        nickname_perfil.setText(u.getNickname());
        num_publis_txt.setText(String.valueOf(publicacionesPerfil.size()) + " \n publicaciones");
        num_seguidores_txt.setText(String.valueOf(u.getSeguidores()) + "\n seguidores");
        num_seguidos_txt.setText(String.valueOf(u.getSiguiendo() + "\n siguiendo"));

    }

    public static Usuario getPerfil() {
        return perfil;
    }

    public static void setPerfil(Usuario perfil) {
        PerfilActivity.perfil = perfil;
    }


}

