package com.example.prueba_tattooally.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.prueba_tattooally.R;

public class PostsAdapter extends BaseAdapter {

    private Context contexto;

    public PostsAdapter(Context contexto) {
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_home, null, true);
        }

        ImageView imageView = convertView.findViewById(R.id.img_perfil_1);

        return null;
    }
}
