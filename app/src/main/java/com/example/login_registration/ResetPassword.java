package com.example.login_registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPassword extends AppCompatActivity {
    EditText userpw,confirmpw;
    Button btnResetnow;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userpw = findViewById(R.id.newuserpw);
        confirmpw = findViewById(R.id.confirmpw);
        btnResetnow = findViewById(R.id.resetupw);

        btnResetnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userpw.getText().toString().isEmpty()) {
                    userpw.setError("Required");
                    return;
                }
                if (confirmpw.getText().toString().isEmpty()) {
                    confirmpw.setError("Required");
                    return;
                }
                if(!userpw.getText().toString().equals(confirmpw.getText().toString())){
                    confirmpw.setError("Password Do Not Match");
                    return;
                }
                user.updatePassword(userpw.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ResetPassword.this, "Password Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ResetPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}