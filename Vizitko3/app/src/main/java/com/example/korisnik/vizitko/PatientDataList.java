package com.example.korisnik.vizitko;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PatientDataList extends ArrayAdapter<PatientData> {
    private Activity context;
    private List<PatientData> patientDataList;


    public PatientDataList(Activity context, List<PatientData> patientDataList){
        super(context, R.layout.layout_listitem_data, patientDataList);
        this.context = context;
        this.patientDataList = patientDataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.layout_listitem_data, null, true);

        TextView tvgTlak = (TextView) listViewItem.findViewById(R.id.tvgTlak);
        TextView tvdTlak = (TextView) listViewItem.findViewById(R.id.tvdTlak);
        TextView tvPuls = (TextView) listViewItem.findViewById(R.id.tvPuls);
        TextView tvTemperatura = (TextView) listViewItem.findViewById(R.id.tvTemperatura);
        TextView tvVrijeme = (TextView) listViewItem.findViewById(R.id.tvVrijeme);

        PatientData patientData = patientDataList.get(position);

        tvgTlak.setText("Tlak:"+patientData.getDatagTlak());
        tvdTlak.setText("/"+patientData.getDataDTlak());
        tvPuls.setText("Puls:"+patientData.getDataPuls());
        tvTemperatura.setText("Temperatura:"+patientData.getDataTemperatura());
        tvVrijeme.setText("Datum uzimanja podataka:"+patientData.getDataDate());


        return listViewItem;
    }
}
