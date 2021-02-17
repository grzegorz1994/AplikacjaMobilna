package com.example.organizerrolnika;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    //Deklaracja pol
    private EditText mEmailEt;
    private EditText mPasswordEt;
    private Button mZarejestrujBtn;
    private TextView mHaveAccountTv;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Rejestracja");


        //inicjalizacja pol
        mAuth = FirebaseAuth.getInstance();
        mEmailEt = (EditText) findViewById(R.id.emailEtRejestracja);
        mPasswordEt = (EditText) findViewById(R.id.passwordEtRejestracja);
        mZarejestrujBtn = (Button) findViewById(R.id.zarejestrujBtnRejestracja);
        mHaveAccountTv = (TextView) findViewById(R.id.haveAccountTv);
        mZarejestrujBtn.setOnClickListener(this);
        mHaveAccountTv.setOnClickListener(this);

        //progressDialog rejestracja
        progressDialog = new ProgressDialog(this);

        //jezeli uzytkownik jest zalogowany
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(RegisterActivity.this,ProfileActivity.class));
            finish();
        }
    }

    //rejestracja uzytkownika
    private void userRegister(){
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

        progressDialog.setMessage("Rejestracja użytkownika...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Utworzono nowego użytkownika",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                        }
                        else{
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(),"Jesteś już zarejestrowany",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext()," " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == mZarejestrujBtn){
            userRegister();
        }

        if (v == mHaveAccountTv){
            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            finish();
        }
    }
}
