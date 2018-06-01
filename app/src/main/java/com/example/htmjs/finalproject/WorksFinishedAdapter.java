package com.example.htmjs.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WorksFinishedAdapter extends BaseAdapter {

    private ArrayList<WorksFinished> listWorks;
    private Context context;
    private LayoutInflater layoutInflater;

    public WorksFinishedAdapter(Context _context, ArrayList<WorksFinished> _works) {
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

        view = layoutInflater.inflate(R.layout.data_finished_works_suoritteet_row, null);
        WorksFinished w = listWorks.get(i);

        TextView tyoID = view.findViewById(R.id.tvTYOID);
        tyoID.setText(w.getTyo_ID());

        TextView kuvaus = view.findViewById(R.id.tvKuvaus);
        kuvaus.setText(w.getKuvaus());

        TextView pvm = view.findViewById(R.id.tvPVM);
        pvm.setText(w.getPvm());

        TextView tila = view.findViewById(R.id.tvTila);
        tila.setText(w.getTila());

        TextView selitys = view.findViewById(R.id.tvSelitys);
        selitys.setText(w.getSelitys());

        TextView tunnit = view.findViewById(R.id.tvTunnit);
        tunnit.setText(w.getTunnit());

        TextView maara = view.findViewById(R.id.tvMaara);
        maara.setText(w.getMaara());

        TextView suorite = view.findViewById(R.id.tvSuorite);
        suorite.setText(w.getSuorite());

        TextView yksikko = view.findViewById(R.id.tvYksikko);
        yksikko.setText(w.getYksikko());


        return view;
    }
}
