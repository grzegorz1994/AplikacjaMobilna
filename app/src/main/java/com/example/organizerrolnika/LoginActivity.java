package com.example.organizerrolnika;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    //Deklaracja pol
    private EditText mEmailEt;
    private EditText mPasswordEt;
    private Button mZalogujBtn;
    private TextView mResetHaslaTv;
    private TextView mNotHaveAccountTv;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Logowanie");

        //Inicjalizacja pol
        mAuth = FirebaseAuth.getInstance();
        mEmailEt = (EditText) findViewById(R.id.emailEtLogowanie);
        mPasswordEt = (EditText) findViewById(R.id.passwordEtLogowanie);
        mZalogujBtn = (Button) findViewById(R.id.zalogujBtnLogowanie);
        mResetHaslaTv = (TextView) findViewById(R.id.resetHaslaTv);
        mNotHaveAccountTv = (TextView) findViewById(R.id.notHaveAccountTv);
        mZalogujBtn.setOnClickListener(this);
        mResetHaslaTv.setOnClickListener(this);
        mNotHaveAccountTv.setOnClickListener(this);

        //progressDialog logowanie
        progressDialog = new ProgressDialog(this);

        //jezeli uzytkownik jest zalogowany
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this,ProfileActivity.class));
            finish();
        }
    }

    //Logowanie uzytkownika
    private void userLogin(){
        String email = mEmailEt.getText().toString().trim();
        String password = mPasswordEt.getText().toString().trim();

        if (email.isEmpty()){
            mEmailEt.setError("Wpisz email");
            mEmailEt.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmailEt.setError("Wpisz poprawny email");
            mEmailEt.requestFocus();
            return;
        }

        if (password.isEmpty()){
            mPasswordEt.setError("Wpisz hasło");
            mPasswordEt.requestFocus();
            return;
        }

        if (password.length() < 6){
            mPasswordEt.setError("Hasło musi zawierać minimum 6 znaków");
            mPasswordEt.requestFocus();
            return;
        }

        progressDialog.setMessage("Logowanie...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            startActivity(new Intent(LoginActivity.this,ProfileActivity.class));
                            finish();
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this,"Nie udało się zalogować",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, " " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Przypominanie hasla
    private void showResetPassword(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resetowanie Hasła");

        final EditText emailEt = new EditText(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.addView(emailEt);
        linearLayout.setPadding(10,10,10,10);

        emailEt.setHint("Wpisz email");
        emailEt.setMinEms(16);
        emailEt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        builder.setView(linearLayout);

        builder.setPositiveButton("Resetuj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = emailEt.getText().toString().trim();
                resetPassword(email);
            }
        });

        builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    //Resetowanie hasla
    private void resetPassword(String email){
        progressDialog.setMessage("Resetowanie hasła...");
        progressDialog.show();
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this,"Wiadomość została wysłana",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this,"Zmiana hasła nie powiodła się",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this," " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == mZalogujBtn){
            userLogin();
        }

        if (v == mNotHaveAccountTv){
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            finish();
        }

        if (v == mResetHaslaTv){
            showResetPassword();
        }
    }
}
