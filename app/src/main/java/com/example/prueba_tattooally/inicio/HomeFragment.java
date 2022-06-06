package com.example.prueba_tattooally.inicio;

import static com.example.prueba_tattooally.utils.JSONArrayAPublicaciones;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.example.prueba_tattooally.Models.Usuario;
import com.example.prueba_tattooally.R;
import com.example.prueba_tattooally.adapter.publicacionesInicioAdapter;
import com.example.prueba_tattooally.databinding.FragmentHomeBinding;
import com.example.prueba_tattooally.login.SplashActivity;
import com.example.prueba_tattooally.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    static Usuario usuarioLogeado;
    SwipeRefreshLayout gestoActualizar;
    String URL;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        usuarioLogeado = new Usuario();
        Intent nicknameAux = getActivity().getIntent();
        String nickname = nicknameAux.getStringExtra("usuarioLogeado");
        cargarUsuario("http://"+ SplashActivity.getIp()+"/tattooally_php/cargar_perfil.php?nickname="+nickname,getContext());
        URL = "http://"+ SplashActivity.getIp()+"/tattooally_php/obtener_publicaciones.php";
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


    public static Usuario getUsuarioLogeado() {
        return usuarioLogeado;
    }

    public static void setUsuarioLogeado(Usuario usuarioLogeado) {
        HomeFragment.usuarioLogeado = usuarioLogeado;
    }

    public void mostrarPublicaciones(ArrayList<Publicacion> publicaciones){
        if(binding != null){
            View root = binding.getRoot();
            GridView gridView = root.findViewById(R.id.gridViewHome);
            publicacionesInicioAdapter publicacionesInicioAdapter = new publicacionesInicioAdapter(getContext(), publicaciones);
            gridView.setAdapter(publicacionesInicioAdapter);
        }
    }
    public static void cargarUsuario(String URL, Context context){

        JsonArrayRequest jsonArrayRequest  = new JsonArrayRequest(URL
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray arrayJSON = response;
                try {
                    JSONObject objeto = arrayJSON.getJSONObject(0);
                    usuarioLogeado = guardarUsuario(objeto);
                    System.out.println("SOUT DENTRO DE CARGAR USUARIO:" + usuarioLogeado.toString() ) ;
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"No se ha podido recuperar el usuario!",Toast.LENGTH_SHORT).show();
                System.out.println(error.getMessage());

            }
        });

        MiSingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

    }

    private static Usuario guardarUsuario(JSONObject objeto) {
        Usuario usuarioLogeado = new Usuario();
        try{
            int idUsuario = objeto.getInt("idUsuario");
            Bitmap imagenPerfil = utils.StringABitMap(objeto.getString("imagen"));
            String email = objeto.getString("email");
            String nombre = objeto.getString("nombre");
            int seguidores = objeto.getInt("seguidores");
            String nickname = objeto.getString("nickname");
            usuarioLogeado.setIdUsuario(idUsuario);
            usuarioLogeado.setFotoPerfil(imagenPerfil);
            usuarioLogeado.setEmail(email);
            usuarioLogeado.setNombre(nombre);
            usuarioLogeado.setNickname(nickname);
            usuarioLogeado.setSeguidores(seguidores);
            usuarioLogeado.setSiguiendo(0);
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        return  usuarioLogeado;

    }


}