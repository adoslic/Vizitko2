package com.example.korisnik.vizitko;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewPatientFragment extends Fragment implements View.OnClickListener {


    private Button bUnesi;
    private EditText etIme;
    private EditText etPrezime;
    private EditText etDatumRodenja;
    private EditText etVisina;
    private EditText etTezina;
    private EditText etOsiguranje;
    private EditText etBrojNekogBliskog;

    DatabaseReference databaseReference;

    public NewPatientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_patient, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        etIme = (EditText) view.findViewById(R.id.etIme);
        etPrezime = (EditText) view.findViewById(R.id.etPrezime);
        etDatumRodenja = (EditText) view.findViewById(R.id.etDatumRodenja);
        etVisina = (EditText) view.findViewById(R.id.etVisina);
        etTezina = (EditText) view.findViewById(R.id.etTezina);
        etOsiguranje = (EditText) view.findViewById(R.id.etOsiguranje);
        etBrojNekogBliskog = (EditText) view.findViewById(R.id.etBrojNekogBliskog);
        bUnesi = (Button) view.findViewById(R.id.bUnesi);
        bUnesi.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view) {
        String ime = etIme.getText().toString().trim();
        String prezime = etPrezime.getText().toString().trim();
        String datum = etDatumRodenja.getText().toString().trim();
        String visina = etVisina.getText().toString().trim();
        String tezina = etTezina.getText().toString().trim();
        String osiguranje = etOsiguranje.getText().toString().trim();
        String brojOsobe = etBrojNekogBliskog.getText().toString().trim();



        if(!(TextUtils.isEmpty(ime) || TextUtils.isEmpty(prezime) || TextUtils.isEmpty(datum) || TextUtils.isEmpty(visina) ||
                TextUtils.isEmpty(tezina) || TextUtils.isEmpty(osiguranje) || TextUtils.isEmpty(brojOsobe))) {
            //databaseReference = FirebaseDatabase.getInstance().getReference("https://vizitko-8b6f3.firebaseio.com/pacijenti");
            //String id = databaseReference.push().getKey();
            //UnosPacijenta unosPacijenta = new UnosPacijenta(id, ime, prezime, datum, visina, tezina, osiguranje, brojOsobe);
            //databaseReference.child(id).setValue(unosPacijenta);
            //databaseReference.setValue(ime);
            Toast.makeText(getActivity(),etIme.getText().toString()+" "+etPrezime.getText().toString(), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(), "unesite sva polja", Toast.LENGTH_LONG).show();
        }
    }
}
