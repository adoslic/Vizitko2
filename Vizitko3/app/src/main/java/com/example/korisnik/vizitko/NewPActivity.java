package com.example.korisnik.vizitko;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewPActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bNewP;
    private Button bHome;
    private Button bOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_p);
        this.setUpUI();
    }

    private void setUpUI() {
        this.bNewP = (Button) findViewById(R.id.bNewP);
        this.bNewP.setOnClickListener(this);
        this.bHome = (Button) findViewById(R.id.bHome);
        this.bHome.setOnClickListener(this);
        this.bOther = (Button) findViewById(R.id.bOther);
        this.bOther.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent explicitIntent = new Intent();
        switch (view.getId()) {
            case R.id.bHome:
                explicitIntent.setClass(getApplicationContext(), HomeActivity.class);
                break;
            case R.id.bOther:
                explicitIntent.setClass(getApplicationContext(), OtherActivity.class);
                break;
            default:
                // i'm lazy, do nothing
                break;
        }
        this.startActivity(explicitIntent);
    }
}
