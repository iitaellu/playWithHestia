package com.example.playwithhestia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ToyAdapter extends BaseAdapter {
    ////https://www.youtube.com/watch?v=rdGpT1pIJlw
    LayoutInflater mInflater;
    String[] toys;
    String[] details;

    public ToyAdapter(Context c, String[] t, String[] d){
        toys = t;
        details = d;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return toys.length;
    }

    @Override
    public Object getItem(int i) {
        return toys[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = mInflater.inflate(R.layout.toy_details, null);
        TextView toyName = (TextView) v.findViewById(R.id.toyName);
        TextView detail = (TextView) v.findViewById(R.id.toyDetil);

        String name = toys[i];
        String det = details[i];

        toyName.setText(name);
        detail.setText(det);

        return v;
    }
}
