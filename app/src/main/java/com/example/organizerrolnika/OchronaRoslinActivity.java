package com.example.organizerrolnika;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OchronaRoslinActivity extends AppCompatActivity implements View.OnClickListener{

    private Button zapiszOprysk;
    private Spinner grzybobojcze;
    private Spinner owadobojcze;
    private Spinner chwastobojcze;
    private Spinner stymulujace;
    private Spinner zwabiajace;
    private Spinner odstraszajace;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    String userId;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ochrona_roslin);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            key = extras.getString("KEY");
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Wybór oprysku");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //inicjalizacja bazy danych
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userId = user.getUid();

        //inicjalizacja widokow spinner
        zapiszOprysk = (Button) findViewById(R.id.zapisz_oprysk_Btn);
        grzybobojcze = (Spinner) findViewById(R.id.srodekGrzybobojczySpinner);
        owadobojcze = (Spinner) findViewById(R.id.srodekOwadobojczySpinner);
        chwastobojcze = (Spinner) findViewById(R.id.srodekChwastobojczySpinner);
        stymulujace = (Spinner) findViewById(R.id.srodkiStymulujaceSpinner);
        zwabiajace = (Spinner) findViewById(R.id.srodkiZwabiajaceSpinner);
        odstraszajace = (Spinner) findViewById(R.id.srodkiOdstraszajaceSpinner);
        zapiszOprysk.setOnClickListener(this);
    }

    //zapis zabiegu ochrony roslin do bazy danych
    private void dodajOchroneUprawy(){
         String opryskFungicydy = grzybobojcze.getSelectedItem().toString();
         String opryskInsektycydy = owadobojcze.getSelectedItem().toString();
         String opryskHerbicydy = chwastobojcze.getSelectedItem().toString();
         String opryskRegulatoryWzrostu = stymulujace.getSelectedItem().toString();
         String opryskAtraktanty = zwabiajace.getSelectedItem().toString();
         String opryskRepelenty = odstraszajace.getSelectedItem().toString();

        if (TextUtils.isEmpty(opryskFungicydy) || (TextUtils.isEmpty(opryskInsektycydy)) || (TextUtils.isEmpty(opryskHerbicydy))||
                (TextUtils.isEmpty(opryskRegulatoryWzrostu)) || (TextUtils.isEmpty(opryskAtraktanty)) ||
                (TextUtils.isEmpty(opryskRepelenty))){

            Pole pole = new Pole(opryskFungicydy, opryskInsektycydy, opryskHerbicydy, opryskRegulatoryWzrostu,
                    opryskAtraktanty, opryskRepelenty);

            databaseReference.child("users").child(userId).child("pola").child(key).child("ochrona").setValue(pole);

            Toast.makeText(getApplicationContext(),"Środki ochrony roślin zostały zapisane", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Próba zapisu nie powiodła się", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    @Override
    public void onClick(View v) {
        if (v == zapiszOprysk){
            dodajOchroneUprawy();
        }
    }
}
