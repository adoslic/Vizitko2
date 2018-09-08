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

public class PatientList extends ArrayAdapter<AddPatient>{

    private Activity context;
    private List<AddPatient> patientList;


    public PatientList(Activity context, List<AddPatient> patientList){
        super(context, R.layout.layout_listitem, patientList);
        this.context = context;
        this.patientList = patientList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.layout_listitem, null, true);

        TextView tvName = (TextView) listViewItem.findViewById(R.id.tvName);
        TextView tvLName = (TextView) listViewItem.findViewById(R.id.tvLName);
        TextView tvDatum = (TextView) listViewItem.findViewById(R.id.tvDatum);
        TextView tvOsiguranje = (TextView) listViewItem.findViewById(R.id.tvOsiguranje);

        AddPatient addpatient = patientList.get(position);

        tvName.setText(addpatient.getPacijentIme());
        tvLName.setText(addpatient.getPacijentPrezime());
        tvDatum.setText(addpatient.getPacijentDatum());
        tvOsiguranje.setText(addpatient.getPacijentOsiguranje());

        return listViewItem;
    }
}
