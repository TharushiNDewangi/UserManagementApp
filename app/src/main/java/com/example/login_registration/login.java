package com.example.login_registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    EditText inputEmail ,inputPassword;
    Button btnLogin,btnRegistor,btnForgetpw;
    FirebaseAuth mAuth;
    AlertDialog.Builder reset_alert;
    LayoutInflater inflater;
    //FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        //user =mAuth.getCurrentUser();
        reset_alert = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();

        inputEmail = findViewById(R.id.login_email);
        inputPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_button);
        btnRegistor = findViewById(R.id.registor);
        btnForgetpw = findViewById(R.id.forgetpw);


        btnRegistor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
                finish();
            }
        });

        btnForgetpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = inflater.inflate(R.layout.reset_password_pop,null);
                //AlertDialog.Builder reset_alert = new AlertDialog.Builder(getApplicationContext());
                reset_alert.setTitle("Reset  Forget Password ?")
                        .setMessage("Enter your Email get password reset link .")
                        .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //validate email
                                EditText email = v.findViewById(R.id.reset_email_pop);
                                if(email.getText().toString().isEmpty()){
                                    email.setError("Required !");
                                    return;
                                }
                                //send the reset link
                                mAuth.sendPasswordResetEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(login.this, "Reset Email Send.Check your Email", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }).setNegativeButton("Cancel",null)
                        .setView(v).create().show();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputEmail.getText().toString().isEmpty())
                {
                    inputEmail.setError("User name is Missing");
                    return;
                }
                if(inputPassword.getText().toString().isEmpty())
                {
                    inputPassword.setError("Password is Missing");
                    return;
                }
                mAuth.signInWithEmailAndPassword(inputEmail.getText().toString(),inputPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(login.this,authResult.toString(),Toast.LENGTH_LONG).show();
                        System.out.println(authResult.toString());
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(login.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(),Home.class));
            finish();
        }
    }
}