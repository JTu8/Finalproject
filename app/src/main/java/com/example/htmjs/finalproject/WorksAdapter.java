package com.example.htmjs.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WorksAdapter extends BaseAdapter{

    private ArrayList<Works> listWorks;
    private Context context;
    private LayoutInflater layoutInflater;

    public WorksAdapter(Context _context, ArrayList<Works> _works) {
        super();
        context = _context;
        listWorks = _works;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return listWorks.size();
    }

    @Override
    public Object getItem(int i) {
        return listWorks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = layoutInflater.inflate(R.layout.data_works_row, null);
        Works w = listWorks.get(i);

        TextView tyoID = view.findViewById(R.id.tvTYOID);
        tyoID.setText(w.getTyo_ID());

        TextView kuvaus = view.findViewById(R.id.tvKuvaus);
        kuvaus.setText(w.getKuvaus());

        TextView pvm = view.findViewById(R.id.tvPVM);
        pvm.setText(w.getPvm());

        TextView tila = view.findViewById(R.id.tvTila);
        tila.setText(w.getTila());


        return view;
    }
}
