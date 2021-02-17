package com.example.organizerrolnika;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuPolaActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mNowyZabiegBtn;
    private Button mPowiadomieniaBtn;
    private Button mInformacjeOPoluBtn;
    private Button mNotatkiDoPolaBtn;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pola);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        key = extras.getString("KEY");


        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Menu pola");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mNowyZabiegBtn = (Button) findViewById(R.id.mNowyZabiegBtnMenuPola);
        mPowiadomieniaBtn = (Button) findViewById(R.id.mZbieraniePlonow_BtnMenuPola);
        mInformacjeOPoluBtn = (Button) findViewById(R.id.mInformacjeOPolu_BtnMenuPola);
        mNotatkiDoPolaBtn = (Button) findViewById(R.id.mNotatkiDoPola_BtnMenuPola);
        mNowyZabiegBtn.setOnClickListener(this);
        mInformacjeOPoluBtn.setOnClickListener(this);
        mNotatkiDoPolaBtn.setOnClickListener(this);

    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {
        if (v == mNowyZabiegBtn){
            Intent intent2 = new Intent(MenuPolaActivity.this, WybierzZabiegActivity.class);
            intent2.putExtra("KEY", key);
            startActivity(intent2);
        }
        if (v == mInformacjeOPoluBtn){
            Intent intentN = new Intent(MenuPolaActivity.this, InformacjeOPolu.class);
            intentN.putExtra("KEY", key);
            startActivity(intentN);
        }
        if (v == mNotatkiDoPolaBtn){
            Intent intentP = new Intent(MenuPolaActivity.this, NotatkiDoPola.class);
            intentP.putExtra("KEY", key);
            startActivity(intentP);
        }
    }
}
