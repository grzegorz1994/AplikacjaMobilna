package com.example.organizerrolnika;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class RodzajNawozeniaActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mOrganiczneBtn;
    private Button mMineralneBtn;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rodzaj_nawozenia);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            key = extras.getString("KEY");
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Wybierz rodzaj");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mOrganiczneBtn = (Button) findViewById(R.id.organiczneBtn);
        mMineralneBtn = (Button) findViewById(R.id.mineralneBtn);
        mOrganiczneBtn.setOnClickListener(this);
        mMineralneBtn.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {
        if (v == mOrganiczneBtn){
            //startActivity(new Intent(RodzajNawozeniaActivity.this,NawozenieOrganiczneActivity.class));
            Intent intent4 = new Intent(RodzajNawozeniaActivity.this, NawozenieOrganiczneActivity.class);
            intent4.putExtra("KEY", key);
            startActivity(intent4);
        }

        if (v == mMineralneBtn){
            Intent intent5 = new Intent(RodzajNawozeniaActivity.this, NawozenieMineralneActivity.class);
            intent5.putExtra("KEY", key);
            startActivity(intent5);
        }
    }
}
