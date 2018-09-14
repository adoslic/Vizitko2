package com.example.korisnik.vizitko;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class  WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bRegister;
    private Button bLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setUpUI();
    }

    private void setUpUI() {
        this.bRegister = (Button) findViewById(R.id.bRegister);
        this.bRegister.setOnClickListener(this);
        this.bLogin = (Button) findViewById(R.id.bLogin);
        this.bLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bRegister:
                finish();
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                //Toast.makeText(this, "Gumb za registraciju pritisnut", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bLogin:
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                //Toast.makeText(this, "Gumb za prijavu pritisnut", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
