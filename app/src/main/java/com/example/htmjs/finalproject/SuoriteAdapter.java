package com.example.htmjs.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SuoriteAdapter extends BaseAdapter {

    private ArrayList<SuoriteRyhma> listSuoritteet;
    private Context context;
    private LayoutInflater layoutInflater;

    public SuoriteAdapter(Context _context, ArrayList<SuoriteRyhma> _suoritteet) {
        super();
        context = _context;
        listSuoritteet = _suoritteet;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listSuoritteet.size();
    }

    @Override
    public Object getItem(int i) {
        return listSuoritteet.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"InflateParams", "ViewHolder"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = layoutInflater.inflate(R.layout.data_suorite_row, null);
        SuoriteRyhma sr = listSuoritteet.get(i);

        TextView suoriteRyhma = view.findViewById(R.id.textViewRyhma1);
        suoriteRyhma.setText(sr.getWorkgroup_name());


        return view;
    }
}
