package com.example.grocerystore.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocerystore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {
    EditText edtemail,edtpassword;
    Button btnlogin;
    ProgressBar progressBar2;
    TextView txtlogin,txtforgotpassword;

    private FirebaseAuth firebaseAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        txtlogin=findViewById(R.id.txtlogin);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar2=findViewById(R.id.progressBar2);
        edtemail = findViewById(R.id.edtemail);
        edtpassword = findViewById(R.id.edtpassword);
        btnlogin = findViewById(R.id.btnforgot);
        progressBar2.setVisibility(View.GONE);
        txtforgotpassword=findViewById(R.id.txtforgotpassword);
        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        txtforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = edtemail.getText().toString();
                String userPassword = edtpassword.getText().toString();
                if (TextUtils.isEmpty(userEmail)) {
                    Toast.makeText(LoginActivity.this,"Enter the name",Toast.LENGTH_LONG).show();
                    return;
                }
                if (userPassword.length() < 6) {
                    Toast.makeText(LoginActivity.this,"Password too short password should be of atleast 6 characters",Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar2.setVisibility(View.VISIBLE);
                //firebaseauthenication that is for checking that it will help for loging in.
                firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this,"Successfully Login",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    progressBar2.setVisibility(View.GONE);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Error!" + task.getException(),Toast.LENGTH_LONG).show();
                                    progressBar2.setVisibility(View.GONE);
                                }

                            }
                        });
            }
        });
    }
}