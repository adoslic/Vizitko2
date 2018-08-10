package com.example.korisnik.vizitko;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setUpUI();
    }

    private void setUpUI() {
        this.bLogin = (Button) findViewById(R.id.bLogin);
        this.bLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent explicitIntent = new Intent();
        explicitIntent.setClass(getApplicationContext(),HomeActivity.class);
        this.startActivity(explicitIntent);
    }
}
