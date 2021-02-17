package com.example.organizerrolnika;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class NawozenieOrganiczneActivity extends AppCompatActivity implements View.OnClickListener{

    private Button zapiszNawozOrganiczny;
    private Spinner nawozOrganicznySpinner;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    String userId;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nawozenie_organiczne);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            key = extras.getString("KEY");
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Wybierz nawóz");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //databaseReference = FirebaseDatabase.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userId = user.getUid();


        zapiszNawozOrganiczny = (Button) findViewById(R.id.zapisz_nawoz_organicznyBtn);
        nawozOrganicznySpinner = (Spinner) findViewById(R.id.nawozOrganicznySpinner);
        zapiszNawozOrganiczny.setOnClickListener(this);
    }

    //zapisywanie nawozu organicznego do bazy danych
    private void dodajNawozOrganiczny(){
        String nawozOrganiczny = nawozOrganicznySpinner.getSelectedItem().toString();



        if (!TextUtils.isEmpty(nawozOrganiczny)){
            Pole pole = new Pole(nawozOrganiczny);




           //databaseReference.child("users").child(userId).child("pola").child("-MO7Ie2esu5KsePhtFhn").child("organiczne").setValue(pole);
            databaseReference.child("users").child(userId).child("pola").child(key).child("organiczne").setValue(pole);

            Toast.makeText(getApplicationContext(),"Nawóz organiczny został zapisany",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Próba zapisania nie powiodła się",Toast.LENGTH_SHORT).show();
        }


    }

    //odczytanie pozycji klikniętego pola
   /*@Override
    protected void onStart() {
        super.onStart();
        databaseReference.child("users").child(userId).child("pola").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot poleSnapshot : dataSnapshot.getChildren()){

                    key = poleSnapshot.getKey();

                }

                //Toast.makeText(getApplicationContext(),"Klucz to: " + myKey, Toast.LENGTH_LONG).show();
                //myKey = dataSnapshot.getChildren().iterator().next().getKey();
                //Pole pole = dataSnapshot.getChildren().iterator().next().getValue(Pole.class);
                //myKey = pole.getDataDodania();
                //Toast.makeText(getApplicationContext(),"Klucz to: " + key, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }*/

   //Klikniecie przycisku zapisz
    @Override
    public void onClick(View v) {
        if (v == zapiszNawozOrganiczny){
            dodajNawozOrganiczny();
            startActivity(new Intent(this,ProfileActivity.class));
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
