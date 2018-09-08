package com.example.korisnik.vizitko;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity{

    public static final String PATIENT_ID = "PatientID";
    public static final String PATIENT_NAME = "PatientName";
    public static final String PATIENT_LNAME = "PatientLName";

    private BottomNavigationView bottom_navbar;


    private ImageButton ibNewPatient;
    private ListView lvPatient;
    private DatabaseReference databaseReference;
    List<AddPatient> patientList;

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                patientList.clear();

                for(DataSnapshot patientSnapshot : dataSnapshot.getChildren()){

                    AddPatient addPatient = patientSnapshot.getValue(AddPatient.class);

                    patientList.add(addPatient);
                }
                PatientList adapter = new PatientList(HomeActivity.this, patientList);
                lvPatient.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        databaseReference = FirebaseDatabase.getInstance().getReference("pacijenti");
        ibNewPatient = (ImageButton) findViewById(R.id.ibNewPatient);

        lvPatient = (ListView) findViewById(R.id.lvPatient);

        patientList = new ArrayList<>();

        bottom_navbar = (BottomNavigationView) findViewById(R.id.bottom_navbar);
        Menu menu = bottom_navbar.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottom_navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        break;
                    case R.id.nav_timeline:
                        Intent intent2 = new Intent(HomeActivity.this, TimelineActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_account:
                        Intent intent3 = new Intent(HomeActivity.this, AccountActivity.class);
                        startActivity(intent3);
                        break;

                }
                return false;
            }
        });
        ibNewPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(HomeActivity.this, NewPatientActivity.class);
                startActivity(intent4);
            }
        });

        lvPatient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AddPatient addPatient = patientList.get(i);

                Intent intent = new Intent(getApplicationContext(), PatientActivity.class);
                intent.putExtra(PATIENT_ID, addPatient.getPacijentId());
                intent.putExtra(PATIENT_NAME, addPatient.getPacijentIme());
                intent.putExtra(PATIENT_LNAME, addPatient.getPacijentPrezime());
                startActivity(intent);
            }
        });
    }
}
