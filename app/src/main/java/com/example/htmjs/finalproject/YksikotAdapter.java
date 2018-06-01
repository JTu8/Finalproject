package com.example.htmjs.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class YksikotAdapter extends BaseAdapter {

    private ArrayList<Suoritteet> listSuoritteet;
    private Context context;
    private LayoutInflater layoutInflater;

    public YksikotAdapter(Context _context, ArrayList<Suoritteet> _suoritteet) {
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

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = layoutInflater.inflate(R.layout.data_yksikot_row, null);
        Suoritteet s = listSuoritteet.get(i);

        TextView yksikko = view.findViewById(R.id.tvYksikko);
        yksikko.setText(s.getYksikko());

        return view;
    }
}
