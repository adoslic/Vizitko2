package com.example.korisnik.vizitko;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bRegister;
    private Button bLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        this.setUpUI();
    }

    private void setUpUI() {
        this.bRegister = (Button) findViewById(R.id.bRegister);
        this.bRegister.setOnClickListener(this);
        this.bLogin = (Button) findViewById(R.id.bLogin);
        this.bLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent explicitIntent = new Intent();
        switch (view.getId()) {
            case R.id.bRegister:
                explicitIntent.setClass(getApplicationContext(), RegisterActivity.class);
                break;
            case R.id.bLogin:
                explicitIntent.setClass(getApplicationContext(), LoginActivity.class);
                break;
            default:
                // i'm lazy, do nothing
                break;
        }
        this.startActivity(explicitIntent);
    }
}
