package com.example.prueba_tattooally.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prueba_tattooally.Models.Publicacion;
import com.example.prueba_tattooally.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class publicacionesInicioAdapter extends BaseAdapter {

    private Context contexto;
    private final ArrayList<Publicacion> publicaciones;


    public publicacionesInicioAdapter(Context contexto, ArrayList<Publicacion> publicaciones) {
        this.contexto = contexto;
        this.publicaciones = publicaciones;
    }

    @Override
    public int getCount() {
        return publicaciones.size();
    }

    @Override
    public Object getItem(int position) {
        return publicaciones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.publicacion, null, true);
        }


        ImageView imageView = convertView.findViewById(R.id.fotoPerfilPublicacion);
        Picasso.get()
                .load(publicaciones.get(position).getFotoPerfil())
                .into(imageView);

        imageView = convertView.findViewById(R.id.imagenPublicacion);
        Picasso.get()
                .load(publicaciones.get(position).getFoto())
                .into(imageView);

        TextView texto = convertView.findViewById(R.id.localizacionPublicacion);
        texto.setText(publicaciones.get(position).getLocalizacion());

        texto = convertView.findViewById(R.id.nickname_publicacion);
        texto.setText(publicaciones.get(position).getNickname());

        texto = convertView.findViewById(R.id.categoriaPublicacion);
        texto.setText(publicaciones.get(position).getEstilo());

        texto = convertView.findViewById(R.id.descripcionPublicacion);
        texto.setText(publicaciones.get(position).getDescripcion());

        return convertView;
    }
}
