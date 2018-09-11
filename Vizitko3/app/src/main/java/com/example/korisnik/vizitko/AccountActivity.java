package com.example.korisnik.vizitko;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static android.widget.Toast.LENGTH_LONG;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {
    final static String DATE_FORMAT = "dd.MM.yyyy";
    private BottomNavigationView bottom_navbar;
    private FirebaseAuth firebaseAuth;
    //private ImageButton ibNewPatient;
    private Button Odjava;
    private Button Izmjeni;
    private DatabaseReference databaseReference;
    private TextView tvIme;
    private TextView tvPrezime;
    private TextView tvSpol;
    private TextView tvDatumRodenja;
    private TextView tvDiploma;
    private TextView tvTelBroj;
    private FirebaseUser currentFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        databaseReference = FirebaseDatabase.getInstance().getReference("Profil");

        tvIme = (TextView)findViewById(R.id.tvIme);
        tvPrezime = (TextView) findViewById(R.id.tvPrezime);
        tvSpol = (TextView) findViewById(R.id.tvSpol);
        tvDatumRodenja = (TextView) findViewById(R.id.tvDatumRodenja);
        tvDiploma = (TextView) findViewById(R.id.tvDiploma);
        tvTelBroj = (TextView) findViewById(R.id.tvTelBroj);
        Odjava = (Button) findViewById(R.id.Odjava);


        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        bottom_navbar = (BottomNavigationView) findViewById(R.id.bottom_navbar);
        //ibNewPatient = (ImageButton) findViewById(R.id.ibNewPatient);

        firebaseAuth = FirebaseAuth.getInstance();

        Izmjeni = (Button) findViewById(R.id.Izmjeni);
        Izmjeni.setOnClickListener(this);

        Odjava.setOnClickListener(this);

        Menu menu = bottom_navbar.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottom_navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent intent1 = new Intent(AccountActivity.this, HomeActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_timeline:
                        Intent intent2 = new Intent(AccountActivity.this, TimelineActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_account:
                        break;

                }
                return false;
            }
        });
        /*ibNewPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(AccountActivity.this, NewPatientActivity.class);
                startActivity(intent4);
            }
        });*/

    }

    @Override
    protected void onStart() {
        super.onStart();

        //currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String id = currentFirebaseUser.getUid();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(id).exists()){
                    Account account = dataSnapshot.child(id).getValue(Account.class);

                    String ime = account.getAccountIme();
                    String prezime = account.getAccountPrezime();
                    String spol = account.getAccountSpol();
                    String datum = account.getAccountDatum();
                    String diploma = account.getAccountBrojDiplome();
                    String broj = account.getAccountTelBroj();

                    tvIme.setText("IME: "+ime);
                    tvPrezime.setText("PREZIME: "+prezime);
                    tvSpol.setText("SPOL: "+spol);
                    tvDatumRodenja.setText("DATUM RODENJA: "+datum);
                    tvDiploma.setText("BR. DIPLOME: "+diploma);
                    tvTelBroj.setText("TEL. BROJ: "+broj);


                }
                else{
                    tvIme.setText("IME: ");
                    tvPrezime.setText("PREZIME: ");
                    tvSpol.setText("SPOL: ");
                    tvDatumRodenja.setText("DATUM RODENJA: ");
                    tvDiploma.setText("BR. DIPLOME: ");
                    tvTelBroj.setText("TEL. BROJ:");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Izmjeni:
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                final String id = currentFirebaseUser.getUid();
                //Toast.makeText(this, "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();
                showUpdateDialog(id);
                break;
            case R.id.Odjava:
                firebaseAuth.signOut();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            default:
                break;

        }
    }

    private void showUpdateDialog(final String accountId){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_account_dialog, null);

        dialogBuilder.setView(dialogView);
        final EditText Ime = (EditText) dialogView.findViewById(R.id.Ime);
        final EditText Prezime = (EditText) dialogView.findViewById(R.id.Prezime);
        final Spinner Spol = (Spinner) dialogView.findViewById(R.id.Spol);
        final EditText DatumRodenja = (EditText) dialogView.findViewById(R.id.DatumRodenja);
        final EditText Diploma = (EditText) dialogView.findViewById(R.id.Diploma);
        final EditText TelBroj = (EditText) dialogView.findViewById(R.id.TelBroj);
        final Button bIzmjeni = (Button) dialogView.findViewById(R.id.bIzmjeni);

        dialogBuilder.setTitle("Izmjena podataka za korisnika");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        bIzmjeni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ime = Ime.getText().toString().trim();
                String prezime = Prezime.getText().toString().trim();
                String spol = Spol.getSelectedItem().toString().trim();
                String datum = DatumRodenja.getText().toString().trim();
                String diploma = Diploma.getText().toString().trim();
                String broj = TelBroj.getText().toString().trim();

                if(isDateValid(datum)&& diploma.length()>=10 && broj.length()>=9) {
                    if (!(TextUtils.isEmpty(ime) || TextUtils.isEmpty(prezime) || TextUtils.isEmpty(spol) || TextUtils.isEmpty(datum) ||
                            TextUtils.isEmpty(diploma) || TextUtils.isEmpty(broj))) {
                        updateAccount(accountId, ime, prezime, spol, datum, diploma, broj);
                        //String id = databaseReference.push().getKey();
                        //Account account = new Account(id, ime, prezime, spol, datum, diploma, broj );
                        //databaseReference.child(id).setValue(account);

                        Toast.makeText(AccountActivity.this, "uspilo", LENGTH_LONG).show();

                    }else {
                        Toast.makeText(AccountActivity.this, "unesite sva polja", LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(AccountActivity.this, "unesite valjane podatke", LENGTH_LONG).show();
                }
                alertDialog.dismiss();
            }
        });


    }

    public static boolean isDateValid(String date)
    {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private void updateAccount(String accountId, String accountIme, String accountPrezime, String accountSpol, String accountDatum, String accountDiploma, String accountTelBroj) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Profil").child(accountId);
        Account account = new Account(accountId, accountIme, accountPrezime, accountSpol, accountDatum, accountDiploma, accountTelBroj);
        databaseReference.setValue(account);

    }
}


