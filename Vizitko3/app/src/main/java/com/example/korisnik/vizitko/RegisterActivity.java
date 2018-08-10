package com.example.korisnik.vizitko;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.setUpUI();
    }

    private void setUpUI() {
        this.bRegister = (Button) findViewById(R.id.bRegister);
        this.bRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent explicitIntent = new Intent();
        explicitIntent.setClass(getApplicationContext(),HomeActivity.class);
        this.startActivity(explicitIntent);
    }
}
