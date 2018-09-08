package com.example.korisnik.vizitko;

import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TimelineActivity extends AppCompatActivity{

    private BottomNavigationView bottom_navbar;

    private ImageButton ibNewPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        //this.setUpUI();

        bottom_navbar = (BottomNavigationView) findViewById(R.id.bottom_navbar);
        ibNewPatient = (ImageButton) findViewById(R.id.ibNewPatient);

        Menu menu = bottom_navbar.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottom_navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent intent1 = new Intent(TimelineActivity.this, HomeActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_timeline:
                        break;
                    case R.id.nav_account:
                        Intent intent3 = new Intent(TimelineActivity.this, AccountActivity.class);
                        startActivity(intent3);
                        break;

                }
                return false;
            }
        });

        ibNewPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(TimelineActivity.this, NewPatientActivity.class);
                startActivity(intent4);
            }
        });



    }
}
