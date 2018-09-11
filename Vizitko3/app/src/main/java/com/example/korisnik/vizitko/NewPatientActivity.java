package com.example.korisnik.vizitko;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class NewPatientActivity extends AppCompatActivity{
    final static String DATE_FORMAT = "dd.MM.yyyy";
    private static final int GALERY_INTENT = 1;
    private BottomNavigationView bottom_navbar;
    
    private Button bOdaberi;
    private Button bUnesi;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private EditText etIme;
    private EditText etPrezime;
    private EditText etDatumRodenja;
    private EditText etVisina;
    private EditText etTezina;
    private EditText etOsiguranje;
    private EditText etBrojNekogBliskog;
    private Spinner sSpol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);
        Init();

        bUnesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unesi();
            }
        });

        Menu menu = bottom_navbar.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(false);

        bottom_navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent intent1 = new Intent(NewPatientActivity.this, HomeActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_timeline:
                        Intent intent2 = new Intent(NewPatientActivity.this, TimelineActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_account:
                        Intent intent3 = new Intent(NewPatientActivity.this, AccountActivity.class);
                        startActivity(intent3);
                        break;
                }
                return false;
            }
        });
    }

    private void Init() {
        databaseReference = FirebaseDatabase.getInstance().getReference("pacijenti");
        storageReference = FirebaseStorage.getInstance().getReference();
        bUnesi = (Button) findViewById(R.id.bUnesi);
        etIme = (EditText) findViewById(R.id.etIme);
        etPrezime = (EditText) findViewById(R.id.etPrezime);
        etDatumRodenja = (EditText) findViewById(R.id.etDatumRodenja);
        etVisina = (EditText) findViewById(R.id.etVisina);
        etTezina = (EditText) findViewById(R.id.etTezina);
        etOsiguranje = (EditText) findViewById(R.id.etOsiguranje);
        etBrojNekogBliskog = (EditText) findViewById(R.id.etBrojNekogBliskog);
        sSpol = (Spinner) findViewById(R.id.sSpol);
        bottom_navbar = (BottomNavigationView) findViewById(R.id.bottom_navbar);
    }

    private void Unesi() {
        String ime = etIme.getText().toString().trim();
        String prezime = etPrezime.getText().toString().trim();
        String spol = sSpol.getSelectedItem().toString().trim();
        String datum = etDatumRodenja.getText().toString().trim();
        String visina = etVisina.getText().toString().trim();
        String tezina = etTezina.getText().toString().trim();
        String osiguranje = etOsiguranje.getText().toString().trim();
        String telBroj = etBrojNekogBliskog.getText().toString().trim();

        if(!(TextUtils.isEmpty(ime) || TextUtils.isEmpty(prezime) || TextUtils.isEmpty(datum) || TextUtils.isEmpty(visina) ||
                TextUtils.isEmpty(tezina) || TextUtils.isEmpty(osiguranje) || TextUtils.isEmpty(telBroj))) {
                float Visina = Float.valueOf(visina);
                float Tezina = Float.valueOf(tezina);
                if(isDateValid(datum)&& Visina<250 && Visina>50 && Tezina<250 && Tezina>40 && osiguranje.length()>=10 && telBroj.length()>=9){
                //if(!(TextUtils.isEmpty(ime) || TextUtils.isEmpty(prezime) || TextUtils.isEmpty(datum) || TextUtils.isEmpty(visina) ||
                   // TextUtils.isEmpty(tezina) || TextUtils.isEmpty(osiguranje) || TextUtils.isEmpty(telBroj))) {

                String id = databaseReference.push().getKey();
                AddPatient addPatient = new AddPatient(id, ime, prezime, spol, datum, visina, tezina, osiguranje, telBroj);
                databaseReference.child(id).setValue(addPatient);

                Toast.makeText(NewPatientActivity.this,"uspilo", Toast.LENGTH_LONG).show();
                finish();
                Intent intent4 = new Intent(NewPatientActivity.this, HomeActivity.class);
                startActivity(intent4);
            }
            else{
                    Toast.makeText(NewPatientActivity.this, "unesite stvarne podatke", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(NewPatientActivity.this, "unesite sva polja", Toast.LENGTH_LONG).show();
        }

    }

    public static boolean isDateValid(String date)
    {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}
