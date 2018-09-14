package com.example.korisnik.vizitko;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bLogin;
    private EditText etLozinka;
    private EditText etEmail;
    private TextView tvRegister;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

        setUpUI();
    }

    private void setUpUI() {
        bLogin = (Button) findViewById(R.id.bLogin);
        bLogin.setOnClickListener(this);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(this);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etLozinka = (EditText) findViewById(R.id.etLozinka);
        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bLogin:
                userLogin();
                break;
            case R.id.tvRegister:
                finish();
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                break;
            default:
                break;

        }
    }

    private void userLogin() {
        String email = etEmail.getText().toString().trim();
        String lozinka = etLozinka.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(lozinka)){
            Toast.makeText(this, "Unesite sva polja", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Prijava u tijeku...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, lozinka).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    //Toast.makeText(RegisterActivity.this, "prijava uspjela", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }
                else{
                    Toast.makeText(LoginActivity.this, "Prijava nije uspjela", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }
}
