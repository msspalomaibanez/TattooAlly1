package com.example.prueba_tattooally.inicio;

import static com.example.prueba_tattooally.utils.JSONArrayAPublicaciones;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.prueba_tattooally.Models.Publicacion;
import com.example.prueba_tattooally.R;
import com.example.prueba_tattooally.adapter.publicacionesInicioAdapter;
import com.example.prueba_tattooally.databinding.FragmentHomeBinding;
import com.example.prueba_tattooally.perfil.PerfilActivity;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase en la que se mostrarán las publicaciones de otros usuarios de la app en formato de listado y desde donde
 * habrá un menú inferior con la navegación a todas las diferentes pantallas de la app
 *
 * Funcionalidades:
 * - Visualización de publicaciones
 * - Navegación a otras pantallas con el menú inferior
 */

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    static ArrayList<Publicacion> publicaciones;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        /*EMULADOR*/
        obtenerPublicaciones("http://10.0.2.2/tattooally_php/obtener_publicaciones.php");
        /*DISPOSITIVOS MOVILES*/
        //obtenerPublicaciones("http://192.168.1.138/tattooally_php/obtener_publicaciones.php");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void obtenerPublicaciones(String URL) {
        JsonArrayRequest jsonArrayRequest  = new JsonArrayRequest( URL
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                 publicaciones = JSONArrayAPublicaciones(response);
                PerfilActivity fragment = new PerfilActivity();
                Bundle bundle = new Bundle();
                bundle.putSerializable("publicaciones", publicaciones);
                fragment.setArguments(bundle);
                mostrarPublicaciones(publicaciones);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getBaseContext(),"No se ha podido recuperar las publicaciones!",Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);

    }

    public static ArrayList<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public void mostrarPublicaciones(ArrayList<Publicacion> publicaciones){
        if(binding != null){
            View root = binding.getRoot();
            GridView gridView = root.findViewById(R.id.gridViewHome);
            publicacionesInicioAdapter publicacionesInicioAdapter = new publicacionesInicioAdapter(getContext(),publicaciones);
            gridView.setAdapter(publicacionesInicioAdapter);
        }





    }
}