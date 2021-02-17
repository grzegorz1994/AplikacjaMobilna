package com.example.organizerrolnika;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PoleInfoAdapter extends ArrayAdapter<Pole> {

    private Activity context;
    private List<Pole> poleList;

    public PoleInfoAdapter(Activity context, List<Pole> poleList){
        super(context, R.layout.list_view, poleList);
        this.context = context;
        this.poleList = poleList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listView = inflater.inflate(R.layout.list_view,null,true);

        TextView pNazwaPola = (TextView) listView.findViewById(R.id.nazwaPolaTextView);
        TextView pRodzajUprawy = (TextView) listView.findViewById(R.id.rodzajUprawyTextView);
        TextView pPowierzchniaPola = (TextView) listView.findViewById(R.id.powierzchniaPolaTextView);
        TextView pstrDate = (TextView) listView.findViewById(R.id.dataDodaniaTextView);

        Pole pole = poleList.get(position);
        pNazwaPola.setText(pole.getNazwaPola());
        pRodzajUprawy.setText(pole.getRodzajUprawy());
        pPowierzchniaPola.setText(pole.getPowierzchniaPola());
        pstrDate.setText(pole.getDataDodania());

        return listView;
    }
}
