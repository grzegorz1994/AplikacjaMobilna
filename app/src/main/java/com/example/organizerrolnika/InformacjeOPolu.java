package com.example.organizerrolnika;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class InformacjeOPolu extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    String userId;
    String key;
    private TextView pnazwaPola;
    private TextView prodzajUprawy;
    private TextView ppowierzchniaPola;
    private TextView pdataDodania;
    private TextView pnawozOrganiczny;
    private TextView pnawozAzotowy;
    private TextView pnawozFosforowy;
    private TextView pnawozPotasowy;
    private TextView pnawozSiarkowoMagnezowy;
    private TextView popryskFungicydy;
    private TextView popryskInsektycydy;
    private TextView popryskHerbicydy;
    private TextView popryskRegulatoryWzrostu;
    private TextView popryskAtraktanty;
    private TextView popryskRepelenty;
    private TextView ptytulNotatki;
    private TextView ptrescNotatki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacje_o_polu);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            key = extras.getString("KEY");
        }

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Informacje o polu");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userId = user.getUid();

        pnazwaPola = (TextView) findViewById(R.id.nazwaPolaTextView);
        prodzajUprawy = (TextView) findViewById(R.id.rodzajUprawyTextView);
        ppowierzchniaPola = (TextView) findViewById(R.id.powierzchniaPolaTextView);
        pdataDodania = (TextView) findViewById(R.id.dataDodaniaTextView);
        pnawozOrganiczny = (TextView) findViewById(R.id.nawozOrganicznyTextView);
        pnawozAzotowy = (TextView) findViewById(R.id.nawozAzotowyTextView);
        pnawozFosforowy = (TextView) findViewById(R.id.nawozFosforowyTextView);
        pnawozPotasowy = (TextView) findViewById(R.id.nawozPotasowyTextView);
        pnawozSiarkowoMagnezowy = (TextView) findViewById(R.id.nawozSiarkowoMagnezowyTextView);
        popryskFungicydy = (TextView) findViewById(R.id.srodekGrzybobojczyTextView);
        popryskInsektycydy = (TextView) findViewById(R.id.srodekOwadobojczyTextView);
        popryskHerbicydy = (TextView) findViewById(R.id.srodekChwastobojczyTextView);
        popryskRegulatoryWzrostu = (TextView) findViewById(R.id.srodekStymulujacyTextView);
        popryskAtraktanty = (TextView) findViewById(R.id.srodekZwabiajacyTextView);
        popryskRepelenty = (TextView) findViewById(R.id.srodekOdstraszajacyTextView);
        ptytulNotatki = (TextView) findViewById(R.id.tytulNotakiTV);
        ptrescNotatki = (TextView) findViewById(R.id.trescNotakiTV);

        databaseReference.child("users").child(userId).child("pola").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot poleSnapshot : dataSnapshot.getChildren()){
                    if (key.equals(poleSnapshot.getKey())){
                        Pole p = poleSnapshot.getValue(Pole.class);
                        if (dataSnapshot.getValue() != null){
                            String nazwaPola = p.getNazwaPola();
                            String rodzajUprawy = p.getRodzajUprawy();
                            String powierzchniaPola = p.getPowierzchniaPola();
                            String dataDodania = p.getDataDodania();
                            pnazwaPola.setText(rodzajUprawy);
                            prodzajUprawy.setText(nazwaPola);
                            ppowierzchniaPola.setText(powierzchniaPola);
                            pdataDodania.setText(dataDodania);
                        }else{
                            pnazwaPola.setText(" ");
                            prodzajUprawy.setText(" ");
                            ppowierzchniaPola.setText(" ");
                            pdataDodania.setText(" ");
                        }

                        databaseReference.child("users").child(userId).child("pola").child(Objects.requireNonNull(key)).child("mineralne").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Pole p = dataSnapshot.getValue(Pole.class);
                                if (dataSnapshot.getValue() != null){
                                    String nawozAzotowy = p.getNawozAzotowy();
                                    String nawozFosforowy = p.getNawozFosforowy();
                                    String nawozPotasowy = p.getNawozPotasowy();
                                    String nawozSiarkowyMagnezowy = p.getNawozSiarkowyMagnezowy();
                                    pnawozAzotowy.setText(nawozAzotowy);
                                    pnawozFosforowy.setText(nawozFosforowy);
                                    pnawozPotasowy.setText(nawozPotasowy);
                                    pnawozSiarkowoMagnezowy.setText(nawozSiarkowyMagnezowy);
                                }else{
                                    pnawozAzotowy.setText(" ");
                                    pnawozFosforowy.setText(" ");
                                    pnawozPotasowy.setText(" ");
                                    pnawozSiarkowoMagnezowy.setText(" ");
                                }

                                databaseReference.child("users").child(userId).child("pola").child(Objects.requireNonNull(key)).child("organiczne").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Pole p = dataSnapshot.getValue(Pole.class);
                                        if (dataSnapshot.getValue() != null){
                                            String nawozOrganiczny = p.getNawozOrganiczny();
                                            pnawozOrganiczny.setText(nawozOrganiczny);
                                        }else{
                                            pnawozOrganiczny.setText(" ");
                                        }

                                        databaseReference.child("users").child(userId).child("pola").child(Objects.requireNonNull(key)).child("ochrona").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                Pole p = dataSnapshot.getValue(Pole.class);
                                                if (dataSnapshot.getValue() != null){
                                                    String opryskFungicydy = p.getOpryskFungicydy();
                                                    String opryskInsektycydy = p.getOpryskInsektycydy();
                                                    String opryskHerbicydy = p.getOpryskHerbicydy();
                                                    String opryskRegulatoryWzrostu = p.getOpryskRegulatoryWzrostu();
                                                    String opryskAtraktanty = p.getOpryskAtraktanty();
                                                    String opryskRepelenty = p.getOpryskRepelenty();
                                                    popryskFungicydy.setText(opryskFungicydy);
                                                    popryskInsektycydy.setText(opryskInsektycydy);
                                                    popryskHerbicydy.setText(opryskHerbicydy);
                                                    popryskRegulatoryWzrostu.setText(opryskRegulatoryWzrostu);
                                                    popryskAtraktanty.setText(opryskAtraktanty);
                                                    popryskRepelenty.setText(opryskRepelenty);
                                                }else{
                                                    popryskFungicydy.setText(" ");
                                                    popryskInsektycydy.setText(" ");
                                                    popryskHerbicydy.setText(" ");
                                                    popryskRegulatoryWzrostu.setText(" ");
                                                    popryskAtraktanty.setText(" ");
                                                    popryskRepelenty.setText(" ");
                                                }

                                                databaseReference.child("users").child(userId).child("pola").child(Objects.requireNonNull(key)).child("notatki").addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        Pole p = dataSnapshot.getValue(Pole.class);
                                                        if (dataSnapshot.getValue() != null){
                                                            String tytulNotatki = p.getTytulNotatki();
                                                            String trescNotatki = p.getTrescNotatki();
                                                            ptytulNotatki.setText(tytulNotatki);
                                                            ptrescNotatki.setText(trescNotatki);
                                                        }else{
                                                            ptytulNotatki.setText(" ");
                                                            ptrescNotatki.setText(" ");
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
