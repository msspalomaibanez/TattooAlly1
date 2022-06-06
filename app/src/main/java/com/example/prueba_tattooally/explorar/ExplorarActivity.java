package com.example.prueba_tattooally.explorar;

import static com.example.prueba_tattooally.utils.JSONArrayAPublicaciones;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.prueba_tattooally.Models.MiSingleton;
import com.example.prueba_tattooally.Models.Publicacion;
import com.example.prueba_tattooally.R;
import com.example.prueba_tattooally.databinding.FragmentExplorarBinding;
import com.example.prueba_tattooally.explorar.BusquedaActivity;
import com.example.prueba_tattooally.inicio.MainActivity;
import com.example.prueba_tattooally.login.SplashActivity;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase en la que se mostrará una barra de búsqueda para buscar usuarios o categorías seguido de un listado
 * de categorias frecuentes
 *
 * Funcionalidades:
 * - Buscar usuarios y categorias
 */

public class ExplorarActivity extends Fragment {

    private FragmentExplorarBinding binding;
    Button buscar_btn, blackwork_btn,oldschool_btn,neotrad_btn,dotwork_btn,blkgrey_btn,acuarela_btn,japo_btn,real_btn,orna_btn;
    static ArrayList<Publicacion> publicacionesFiltradas;
    String filtro;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentExplorarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        buscar_btn = (Button) root.findViewById(R.id.buscar_btn);
        buscar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.info_registro);
                builder.setMessage(R.string.msg_explorar);
                builder.setCancelable(false);

                builder.setPositiveButton(R.string.opcion_aceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        blackwork_btn = root.findViewById(R.id.blackwork_btn);
        blackwork_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtro = "Blackwork";
                String URL = "http://"+ SplashActivity.getIp()+"/tattooally_php/buscar_por_categoria.php?categoria='"+ filtro +"'";
                obtenerPublicacionesFiltradas(URL);
            }
        });
        oldschool_btn = root.findViewById(R.id.oldschool_btn);
        oldschool_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtro = "OldSchool";
                String URL = "http://"+ SplashActivity.getIp()+"/tattooally_php/buscar_por_categoria.php?categoria='"+ filtro +"'";
                obtenerPublicacionesFiltradas(URL);
            }
        });
        neotrad_btn = root.findViewById(R.id.neotrad_btn);
        neotrad_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtro = "Neotradicional";
                String URL = "http://"+ SplashActivity.getIp()+"/tattooally_php/buscar_por_categoria.php?categoria='"+ filtro +"'";
                obtenerPublicacionesFiltradas(URL);
            }
        });
        dotwork_btn = root.findViewById(R.id.dotwork_btn);
        dotwork_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtro = "Dotwork";
                String URL = "http://"+ SplashActivity.getIp()+"/tattooally_php/buscar_por_categoria.php?categoria='"+ filtro +"'";
                obtenerPublicacionesFiltradas(URL);
            }
        });
        blkgrey_btn = root.findViewById(R.id.blkgrey_btn);
        blkgrey_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtro = "BlackAndGrey";

                String URL = "http://"+ SplashActivity.getIp()+"/tattooally_php/buscar_por_categoria.php?categoria='"+ filtro +"'";
                obtenerPublicacionesFiltradas(URL);
            }
        });
        acuarela_btn = root.findViewById(R.id.acuarela_btn);
        acuarela_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtro = "Acuarela";
                String URL = "http://"+ SplashActivity.getIp()+"/tattooally_php/buscar_por_categoria.php?categoria='"+ filtro +"'";
                obtenerPublicacionesFiltradas(URL);
            }
        });
        japo_btn = root.findViewById(R.id.japo_btn);
        japo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtro = "Japonés";
                String URL = "http://"+ SplashActivity.getIp()+"/tattooally_php/buscar_por_categoria.php?categoria='"+ filtro +"'";
                obtenerPublicacionesFiltradas(URL);
            }
        });
        real_btn = root.findViewById(R.id.real_btn);
        real_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtro = "Realista";
                String URL = "http://"+ SplashActivity.getIp()+"/tattooally_php/buscar_por_categoria.php?categoria='"+ filtro +"'";
                obtenerPublicacionesFiltradas(URL);
            }
        });
        orna_btn = root.findViewById(R.id.orna_btn);
        orna_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtro = "Ornamental";
                String URL = "http://"+ SplashActivity.getIp()+"/tattooally_php/buscar_por_categoria.php?categoria='"+ filtro +"'";
                obtenerPublicacionesFiltradas(URL);
            }
        });
        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void obtenerPublicacionesFiltradas(String URL) {
        System.out.println(URL);
        JsonArrayRequest jsonArrayRequest  = new JsonArrayRequest( URL
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                publicacionesFiltradas = JSONArrayAPublicaciones(response);
                Fragment fragment = new BusquedaActivity();
                cambioFragment(fragment);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getBaseContext(),"No se han podido recuperar las publicaciones!",Toast.LENGTH_SHORT).show();
                System.out.println(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                return parametros;
            }
        };
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MiSingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);

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
}