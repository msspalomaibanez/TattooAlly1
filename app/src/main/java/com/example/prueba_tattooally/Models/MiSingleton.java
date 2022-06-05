package com.example.prueba_tattooally.Models;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MiSingleton {
    private static MiSingleton instance; //Objeto miembro que almacena una instancia de la misma clase
    private RequestQueue requestQueue;
    private static Context ctx;

    //El constructor recibirá el contexto de la Aplicación
    private MiSingleton(Context context) {
        ctx = context.getApplicationContext();
        requestQueue = getRequestQueue();

    }

    //El método getInstance deberá recibir el contexto de la aplicación para llamar
    //al constructor con el mismo..
    //Ademas se declara como syncrhonized para que no se pueda llamar desde dos sitios
    //simultáneamente, garantizando que sólo se
    //crea una instancia de la clase.
    public static synchronized MiSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new MiSingleton(context);
        }
        return instance;
    }

    //Damos acceso a la RequestQueue
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    //Permitimos añadir una petición de cualquier tipo de datos a la cola
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}