package com.example.organizerrolnika;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    //deklaracja bazy danych
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private ListView listView;
    private List<Pole> poleList;
    String userId;
    String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //ustawienie tytułu aktywnośći
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Witaj Rolniku");

        //Inicjalizacja bazy danych
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();
        poleList = new ArrayList<>();
        listView = findViewById(R.id.list_view);
        userId = user.getUid();

        //sprawdzanie czy uzytkownik jest zalogowany
        if (mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }


        //krótkie kliknięcie na element listy
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                databaseReference.child("users").child(userId).child("pola").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int counter = 0;
                        for (DataSnapshot poleSnapshot : dataSnapshot.getChildren()){
                            if (counter == position){
                                key = poleSnapshot.getKey();
                                break;

                            }
                            counter++;
                        }
                        Intent intent = new Intent(getBaseContext(), MenuPolaActivity.class);
                        intent.putExtra("KEY", key);
                        startActivity(intent);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        //długie kliknięcie na element listy i usunięcie pola z listy
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                databaseReference.child("users").child(userId).child("pola")
                        .orderByChild("nazwaPola")
                        .equalTo((String) listView.getItemAtPosition(position))
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChildren()){
                                    DataSnapshot firstPole = dataSnapshot.getChildren().iterator().next();
                                    firstPole.getRef().removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                return true;
            }
        });
    }

    //okno usuwania elementu
    /*private void showDelete(final String poleId){
        AlertDialog.Builder element = new AlertDialog.Builder(this);
        element.setTitle("Czy na pewno chcesz usunąć to pole?");
        element.setCancelable(false);
        element.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteField(poleId);
            }
        });

        element.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = element.create();
        alertDialog.show();
    }*/

    //usuwanie elementu z listy
    /*private void deleteField(String poleId){

        DatabaseReference usunPole = FirebaseDatabase.getInstance().getReference("pola").child(poleId);
        usunPole.removeValue();

        Toast.makeText(ProfileActivity.this,"Pole zostało usunięte",
                Toast.LENGTH_SHORT).show();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opcje, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_wyloguj){
            mAuth.signOut();
            startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
            finish();
        }
        if (id == R.id.action_dodaj_nowe_pole){
            startActivity(new Intent(ProfileActivity.this,CreateFieldActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.child("users").child(userId).child("pola").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                poleList.clear();
                for (DataSnapshot poleSnapshot : dataSnapshot.getChildren()){
                    //key = poleSnapshot.getKey();
                    Pole pole = poleSnapshot.getValue(Pole.class);
                    poleList.add(pole);
                }
                PoleInfoAdapter poleInfoAdapter = new PoleInfoAdapter(ProfileActivity.this,poleList);
                listView.setAdapter(poleInfoAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
