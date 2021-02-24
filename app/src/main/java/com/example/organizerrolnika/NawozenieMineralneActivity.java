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

public class NawozenieMineralneActivity extends AppCompatActivity implements View.OnClickListener{

    private Button zapiszNawozMineralny;
    private Spinner nawozAzotowySpinner;
    private Spinner nawozFosforowySpinner;
    private Spinner nawozPotasowySpinner;
    private Spinner nawozSiarkowyIMagnezowy;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    String userId;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nawozenie_mineralne);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            key = extras.getString("KEY");
        }

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Wybierz nawóz");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //inicjalizacja bazy danych
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userId = user.getUid();

        //inicjalizacja pol
        zapiszNawozMineralny = (Button) findViewById(R.id.zapisz_nawoz_mineralnyBtn);
        nawozAzotowySpinner = (Spinner) findViewById(R.id.nawozAzotowySpinner);
        nawozFosforowySpinner = (Spinner) findViewById(R.id.nawozFosforowySpinner);
        nawozPotasowySpinner = (Spinner) findViewById(R.id.nawozPotasowySpinner);
        nawozSiarkowyIMagnezowy = (Spinner) findViewById(R.id.nawozSiarkowyMagnezowySpinner);
        zapiszNawozMineralny.setOnClickListener(this);
    }

    //zapisywanie nawozu mineralnego do bazy danych
    private void dodajNawozMineralny(){
        String nawozAzotowy = nawozAzotowySpinner.getSelectedItem().toString();
        String nawozFosforowy = nawozFosforowySpinner.getSelectedItem().toString();
        String nawozPotasowy = nawozPotasowySpinner.getSelectedItem().toString();
        String nawozSiarkowyMagnezowy = nawozSiarkowyIMagnezowy.getSelectedItem().toString();

        if (!TextUtils.isEmpty(nawozAzotowy) || !TextUtils.isEmpty(nawozFosforowy) || !TextUtils.isEmpty(nawozPotasowy) ||
                !TextUtils.isEmpty(nawozSiarkowyMagnezowy)){
            Pole pole = new Pole(nawozAzotowy, nawozFosforowy, nawozPotasowy, nawozSiarkowyMagnezowy);

            databaseReference.child("users").child(userId).child("pola").child(key).child("mineralne").setValue(pole);

            Toast.makeText(getApplicationContext(),"Nawóz mineralny został zapisany",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Próba zapisania nie powiodła się",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {
        if (v == zapiszNawozMineralny){
            dodajNawozMineralny();
        }
    }
}
