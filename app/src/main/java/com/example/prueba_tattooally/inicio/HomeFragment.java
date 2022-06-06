package com.example.prueba_tattooally.inicio;

import static com.example.prueba_tattooally.utils.JSONArrayAPublicaciones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.prueba_tattooally.Models.MiSingleton;
import com.example.prueba_tattooally.Models.Publicacion;
import com.example.prueba_tattooally.R;
import com.example.prueba_tattooally.adapter.publicacionesInicioAdapter;
import com.example.prueba_tattooally.databinding.FragmentHomeBinding;
import com.example.prueba_tattooally.login.SplashActivity;

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
    public static final String TAG = "solicitudPublicaciones";
    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;
    SwipeRefreshLayout gestoActualizar;
    String URL;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        URL = "http://"+ SplashActivity.getIp()+"/tattooally_php/obtener_publicaciones.php";
        System.out.println(URL);
        requestQueue = MiSingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue();
        if(publicaciones == null){

            obtenerPublicaciones(URL);

        }else{
            mostrarPublicaciones(publicaciones);
        }

        gestoActualizar = root.findViewById(R.id.gestoActualizarHome);
        gestoActualizar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                obtenerPublicaciones(URL);

            }
        });
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

    public void obtenerPublicaciones(String URL) {
         jsonArrayRequest  = new JsonArrayRequest( URL
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                 publicaciones = JSONArrayAPublicaciones(response);
                 mostrarPublicaciones(publicaciones);
                gestoActualizar.setRefreshing(false);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getBaseContext(),"No se han podido recuperar las publicaciones!",Toast.LENGTH_SHORT).show();
                System.out.println(error.getMessage());
                gestoActualizar.setRefreshing(false);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                return parametros;
            }
        };
        jsonArrayRequest.setTag(TAG);
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MiSingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);

    }

    public static ArrayList<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public void mostrarPublicaciones(ArrayList<Publicacion> publicaciones){
        if(binding != null){
            View root = binding.getRoot();
            GridView gridView = root.findViewById(R.id.gridViewHome);
            publicacionesInicioAdapter publicacionesInicioAdapter = new publicacionesInicioAdapter(getContext(), publicaciones);
            gridView.setAdapter(publicacionesInicioAdapter);
        }





    }
}