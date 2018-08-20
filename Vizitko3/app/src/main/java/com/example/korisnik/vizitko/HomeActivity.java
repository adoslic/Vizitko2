package com.example.korisnik.vizitko;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bNewP;
    private Button bHome;
    private Button bOther;
    private Button bLogOff;
    private FirebaseAuth firebaseAuth;
    private TextView tvT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.setUpUI();
    }

    private void setUpUI() {
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();

        bNewP = (Button) findViewById(R.id.bNewP);
        bNewP.setOnClickListener(this);
        bHome = (Button) findViewById(R.id.bHome);
        bHome.setOnClickListener(this);
        bOther = (Button) findViewById(R.id.bOther);
        bOther.setOnClickListener(this);
        bLogOff = (Button) findViewById(R.id.bLogOff);
        bLogOff.setOnClickListener(this);
        tvT = (TextView) findViewById(R.id.tvT);
        tvT.setText("Dobrodošao "+user.getEmail());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bNewP:
                startActivity(new Intent(this, NewPActivity.class));
                break;
            case R.id.bOther:
                startActivity(new Intent(this, OtherActivity.class));
                break;
            case R.id.bLogOff:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
            default:
                // i'm lazy, do nothing
                break;
        }
    }
}
