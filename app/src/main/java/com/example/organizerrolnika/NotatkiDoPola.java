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

public class NotatkiDoPola extends AppCompatActivity implements View.OnClickListener{

    private EditText wpiszTytul;
    private EditText wpiszTresc;
    private Button dodajNotatke;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    String key;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notatki_do_pola);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            key = extras.getString("KEY");
        }

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Dodanie Notatki");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userId = user.getUid();

        wpiszTytul = (EditText) findViewById(R.id.tytulNotatki);
        wpiszTresc = (EditText) findViewById(R.id.trescNotatki);
        dodajNotatke = (Button) findViewById(R.id.dodajNotatke);
        dodajNotatke.setOnClickListener(this);
    }

    private void addNotatka(){
        String TytulNotatki = wpiszTytul.getText().toString().trim();
        String TrescNotatki = wpiszTresc.getText().toString().trim();

        if (!TextUtils.isEmpty(TytulNotatki) && (!TextUtils.isEmpty(TrescNotatki))){
            Pole pole = new Pole(TytulNotatki, TrescNotatki);
            databaseReference.child("users").child(userId).child("pola").child(key).child("notatki").setValue(pole);
            wpiszTytul.setText("");
            wpiszTresc.setText("");
            Toast.makeText(NotatkiDoPola.this, "Notatka została zapisana",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Próba zapisu nie powiodła się", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == dodajNotatke){
            addNotatka();
            startActivity(new Intent(this, ProfileActivity.class));
            finish();
        }
    }
}
