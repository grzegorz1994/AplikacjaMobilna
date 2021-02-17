package com.example.organizerrolnika;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WybierzZabiegActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mNawozenie;
    private Button mOchrona;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wybierz_zabieg);

        key = "";

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            key = extras.getString("KEY");
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Wybierz zabieg");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mNawozenie = (Button) findViewById(R.id.nawozenieBtn);
        mOchrona = (Button) findViewById(R.id.ochronaBtn);
        mNawozenie.setOnClickListener(this);
        mOchrona.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {
        if (v == mNawozenie){
            Intent intent3 = new Intent(WybierzZabiegActivity.this, RodzajNawozeniaActivity.class);
            intent3.putExtra("KEY", key);
            startActivity(intent3);
        }
        if (v == mOchrona){
            Intent intentOchrona = new Intent(WybierzZabiegActivity.this, OchronaRoslinActivity.class);
            intentOchrona.putExtra("KEY", key);
            startActivity(intentOchrona);
        }
    }
}
