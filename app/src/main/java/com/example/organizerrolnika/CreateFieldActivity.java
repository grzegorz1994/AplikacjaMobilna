package com.example.organizerrolnika;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateFieldActivity extends AppCompatActivity implements View.OnClickListener{

    //Deklaracja pol
    private EditText cRodzajUprawyEt;
    private EditText cNazwaPolaEt;
    private EditText cPowierzchniaPolaEt;
    private Button cDodajPoleBtn;
    private Button cWyswietlListeBtn;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_field);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tworzenie nowego pola");

        //Inicjalizacja bazy danych
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userId = user.getUid();

        //inicjalizacja pol
        cRodzajUprawyEt = (EditText) findViewById(R.id.rodzajUprawyEtCreateField);
        cNazwaPolaEt = (EditText) findViewById(R.id.nazwaPolaEtCreateField);
        cPowierzchniaPolaEt = (EditText) findViewById(R.id.powierzchniaPolaEtCreateField);
        cDodajPoleBtn = (Button) findViewById(R.id.dodajPoleBtnCreateField);
        cWyswietlListeBtn = (Button) findViewById(R.id.wyswietlListeBtnCreateField);
        cDodajPoleBtn.setOnClickListener(this);
        cWyswietlListeBtn.setOnClickListener(this);
    }

    //Dodanie pola do bazy danych
    private void addField(){

        String NazwaPola = cNazwaPolaEt.getText().toString().trim();
        String RodzajUprawy = cRodzajUprawyEt.getText().toString().trim();
        String PowierzchniaPola = cPowierzchniaPolaEt.getText().toString().trim();

        if (!TextUtils.isEmpty(NazwaPola) && !TextUtils.isEmpty(RodzajUprawy) &&
            !TextUtils.isEmpty(PowierzchniaPola)){

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String strDate = dateFormat.format(date).toString();
            String id = databaseReference.push().getKey();



            Pole pole = new Pole(NazwaPola, RodzajUprawy, PowierzchniaPola, strDate, id);

            databaseReference.child("users").child(userId).child("pola").child(id).setValue(pole);
            cNazwaPolaEt.setText("");
            cRodzajUprawyEt.setText("");
            cPowierzchniaPolaEt.setText("");

            Toast.makeText(CreateFieldActivity.this,"Pole zostało utworzone",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Proszę wypełnić wszystkie pola",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == cDodajPoleBtn){
            addField();
            startActivity(new Intent(this,ProfileActivity.class));
            finish();
        }
        if (v == cWyswietlListeBtn){
            startActivity(new Intent(this,ProfileActivity.class));
            finish();
        }
    }
}
