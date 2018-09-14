package com.example.korisnik.vizitko;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PatientActivity extends AppCompatActivity {

    final static String DATE_FORMAT = "dd.MM.yyyy";
    public static final String PATIENT_ID = "PatientID";
    public static final String PATIENT_NAME = "PatientName";
    public static final String PATIENT_LNAME = "PatientLName";
    private static final int PICK_IMAGE_REQUEST =1;
    private BottomNavigationView bottom_navbar;
    private TextView etImePacijenta;
    private TextView etGTlak;
    private TextView etDTlak;
    private TextView etDijabetes;
    private TextView etPuls;
    private TextView etTemperatura;
    private Button bUnesi;
    private Button bOdaberi;
    private ListView lvPatientData;
    private Uri uri;
    private String Id;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    private List<PatientData> patientDataList;
    private Button bStanje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        Intent intent = getIntent();
        Id = intent.getStringExtra(HomeActivity.PATIENT_ID);
        final String ime = intent.getStringExtra(HomeActivity.PATIENT_NAME);
        final String prezime = intent.getStringExtra(HomeActivity.PATIENT_LNAME);
        Init();
        etImePacijenta.setText(ime+" "+prezime);

        bottom_navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent intent = new Intent(PatientActivity.this, HomeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_account:
                        Intent intent3 = new Intent(PatientActivity.this, AccountActivity.class);
                        startActivity(intent3);
                        break;
                }
                return false;
            }
        });
        bOdaberi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OdaberiSliku();
            }
        });

        bUnesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UnesiPodatke();
            }
        });

        bStanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TimelineActivity.class);
                intent.putExtra(PATIENT_ID, Id);
                intent.putExtra(PATIENT_NAME, ime);
                intent.putExtra(PATIENT_LNAME, prezime);
                startActivity(intent);
            }
        });

    }
    private String DohvatiEkstenziju(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private boolean UnesiSliku(final String id, String dan) {
        if(uri == null){
            //slika nije odabrana
        }
        else{
            StorageReference fileRef = storageReference.child(dan+"."+DohvatiEkstenziju(uri));
            fileRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUri = taskSnapshot.getDownloadUrl();
                        databaseReference.child(id).child("dataURL").setValue(downloadUri.toString());
                    }
            })  .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PatientActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
        return true;
    }

    private void OdaberiSliku() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            uri = data.getData();
            bOdaberi.setText("Slika odabrana");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                patientDataList.clear();

                for(DataSnapshot patientDataSnapshot : dataSnapshot.getChildren()){

                    PatientData patientData = patientDataSnapshot.getValue(PatientData.class);

                    patientDataList.add(patientData);
                }
                PatientDataList adapter = new PatientDataList(PatientActivity.this, patientDataList);
                lvPatientData.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PatientActivity.this, databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

    private void UnesiPodatke() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date date = new Date();
        String dan = simpleDateFormat.format(date);

        String gTlak = etGTlak.getText().toString().trim();
        String dTlak = etDTlak.getText().toString().trim();
        String dijabetes = etDijabetes.getText().toString().trim();
        String puls = etPuls.getText().toString().trim();
        String temperatura = etTemperatura.getText().toString().trim();

        if(!(TextUtils.isEmpty(gTlak) || TextUtils.isEmpty(dTlak) || TextUtils.isEmpty(dijabetes)
                    || TextUtils.isEmpty(puls) || TextUtils.isEmpty(temperatura))) {
                int GTlak = Integer.valueOf(gTlak);
                int DTlak = Integer.valueOf(dTlak);
                float Dijabetes = Float.valueOf(dijabetes);
                int Puls = Integer.valueOf(puls);
                float Temperatura = Float.valueOf(temperatura);
                if(GTlak>80 && GTlak<250 && DTlak>40 && DTlak<150 && Dijabetes>2 && Dijabetes<30 && Puls>40 && Puls<150 && Temperatura>30 && Temperatura<45){

                        String URL = "No Image selected";
                        String id = dan;
                        id = id.replace(".", "");
                        PatientData patientData = new PatientData(id, gTlak, dTlak, dijabetes, puls, temperatura, dan, URL);
                        databaseReference.child(id).setValue(patientData);

                        Toast.makeText(PatientActivity.this, "Podaci uspješno dodani", Toast.LENGTH_LONG).show();

                        UnesiSliku(id, dan);
                }
                else{
                    Toast.makeText(PatientActivity.this, "Unesite stvarne vrijednosti", Toast.LENGTH_LONG).show();
                }
        }else{
            Toast.makeText(PatientActivity.this, "Unesite sva polja", Toast.LENGTH_LONG).show();
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
        bStanje = (Button) findViewById(R.id.bStanje);
        lvPatientData = (ListView) findViewById(R.id.lvPatientData);

        patientDataList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Podaci").child(Id);
        storageReference = FirebaseStorage.getInstance().getReference("Slike").child(Id);

        bottom_navbar = (BottomNavigationView) findViewById(R.id.bottom_navbar);
        Menu menu = bottom_navbar.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(false);
    }
}
