package com.example.korisnik.vizitko;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PatientActivity extends AppCompatActivity {

    private TextView etImePacijenta;
    private TextView etGTlak;
    private TextView etDTlak;
    private TextView etDijabetes;
    private TextView etPuls;
    private TextView etTemperatura;
    private Button bUnesi;
    private Button bOdaberi;
    private ListView lvPatientData;

    private DatabaseReference databaseReference;

    List<PatientData> patientData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        Init();
        patientData = new ArrayList<>();
        Intent intent = getIntent();
        String Id = intent.getStringExtra(HomeActivity.PATIENT_ID);
        String ime = intent.getStringExtra(HomeActivity.PATIENT_NAME);
        String prezime = intent.getStringExtra(HomeActivity.PATIENT_LNAME);

        etImePacijenta.setText(ime+" "+prezime);

        databaseReference = FirebaseDatabase.getInstance().getReference("Podaci").child(Id);

        bUnesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UnesiPodatke();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void UnesiPodatke() {
        String gTlak = etGTlak.getText().toString().trim();
        String dTlak = etDTlak.getText().toString().trim();
        String dijabetes = etDijabetes.getText().toString().trim();
        String puls = etPuls.getText().toString().trim();
        String temperatura = etTemperatura.getText().toString().trim();

        if(!(TextUtils.isEmpty(gTlak) || TextUtils.isEmpty(dTlak) || TextUtils.isEmpty(dijabetes)
                || TextUtils.isEmpty(puls) || TextUtils.isEmpty(temperatura))) {

            String id = databaseReference.push().getKey();
            PatientData patientData = new PatientData(id, gTlak, dTlak, dijabetes, puls, temperatura);
            databaseReference.child(id).setValue(patientData);

            Toast.makeText(PatientActivity.this,"uspilo", Toast.LENGTH_LONG).show();
            finish();
            Intent intent4 = new Intent(PatientActivity.this, HomeActivity.class);
            startActivity(intent4);
        }
        else{
            Toast.makeText(PatientActivity.this, "unesite sva polja", Toast.LENGTH_LONG).show();
        }
    }

    private void Init() {
        etImePacijenta = (TextView) findViewById(R.id.etImePacijenta);
        etGTlak = (TextView) findViewById(R.id.etGTlak);
        etDTlak = (TextView) findViewById(R.id.etDTlak);
        etDijabetes = (TextView) findViewById(R.id.etDijabetes);
        etPuls = (TextView) findViewById(R.id.etPuls);
        etTemperatura = (TextView) findViewById(R.id.etTemperatura);
        bUnesi = (Button) findViewById(R.id.bUnesi);
        bOdaberi = (Button) findViewById(R.id.bOdaberi);
        lvPatientData = (ListView) findViewById(R.id.lvPatientData);

    }
}
