package com.example.korisnik.vizitko;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.korisnik.vizitko.PatientActivity.PATIENT_LNAME;
import static com.example.korisnik.vizitko.PatientActivity.PATIENT_NAME;

public class TimelineActivity extends AppCompatActivity implements View.OnClickListener {
    final static String DATE_FORMAT = "dd.MM.yyyy";
    private BottomNavigationView bottom_navbar;
    private DatabaseReference databaseReference;
    private GraphView graphView;
    private LineGraphSeries series;
    private Button bGTlak;
    private Button bDTlak;
    private Button bPuls;
    private Button bTemperatura;
    private TextView etImePacijenta;
    private String id;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        Intent intent = getIntent();
        id = intent.getStringExtra(HomeActivity.PATIENT_ID);
        String ime = intent.getStringExtra(HomeActivity.PATIENT_NAME);
        String prezime = intent.getStringExtra(HomeActivity.PATIENT_LNAME);

        Init();
        etImePacijenta.setText(ime+" "+prezime);
        etImePacijenta.setTextColor(0xffffffff);

        bGTlak.setOnClickListener(this);
        bDTlak.setOnClickListener(this);
        bPuls.setOnClickListener(this);
        bTemperatura.setOnClickListener(this);

        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if(isValueX){
                    return "Dan:"+super.formatLabel(value+1, isValueX);
                }else {
                    return super.formatLabel(value, isValueX);
                }
            }
        });

        bottom_navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent intent1 = new Intent(TimelineActivity.this, HomeActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_account:
                        Intent intent3 = new Intent(TimelineActivity.this, AccountActivity.class);
                        startActivity(intent3);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void Init() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Podaci").child(id);

        etImePacijenta = (TextView) findViewById(R.id.etImePacijenta);
        bGTlak = (Button) findViewById(R.id.bGTlak);
        bDTlak = (Button) findViewById(R.id.bDTlak);
        bPuls = (Button) findViewById(R.id.bPuls);
        bTemperatura = (Button) findViewById(R.id.bTemperatura);
        graphView = (GraphView) findViewById(R.id.graph);
        bottom_navbar = (BottomNavigationView) findViewById(R.id.bottom_navbar);
        Menu menu = bottom_navbar.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        series = new LineGraphSeries();
        graphView.addSeries(series);
        graphView.getGridLabelRenderer().setNumHorizontalLabels(3);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bGTlak:
                i=0;
                Graf(i);
                break;
            case R.id.bDTlak:
                i=1;
                Graf(i);
                break;
            case R.id.bPuls:
                i=2;
                Graf(i);
                break;
            case R.id.bTemperatura:
                i=3;
                Graf(i);
                break;
        }
    }

    private void Graf(final int i) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataPoint[] dataPoints = new DataPoint[(int) dataSnapshot.getChildrenCount()];
                int index = 0;
                for(DataSnapshot myDataSnapshot : dataSnapshot.getChildren()){
                PatientData patientData = myDataSnapshot.getValue(PatientData.class);
                switch (i) {
                    case 0:
                        dataPoints[index] = new DataPoint(index, Integer.parseInt(patientData.getDatagTlak()));
                        break;
                    case 1:
                        dataPoints[index] = new DataPoint(index, Integer.parseInt(patientData.getDataDTlak()));
                        break;
                    case 2:
                        dataPoints[index] = new DataPoint(index, Integer.parseInt(patientData.getDataPuls()));
                        break;
                    case 3:
                        dataPoints[index] = new DataPoint(index, Float.parseFloat(patientData.getDataTemperatura()));
                        break;
                    default:
                        break;
                }
                index++;
                }
                series.resetData(dataPoints);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TimelineActivity.this, databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
