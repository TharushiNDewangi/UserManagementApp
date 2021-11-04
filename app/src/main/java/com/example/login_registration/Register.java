package com.example.login_registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText inputName, inputPhoneNumber,inputPassword,inputConfirmPassword;
    Button btnRegister,btnLogin;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputName = findViewById(R.id.reg_user_name);
        inputPhoneNumber  = findViewById(R.id.reg_phone);
        inputPassword = findViewById(R.id.reg_password);
        inputConfirmPassword = findViewById(R.id.reg_confirmpassword);
        btnRegister = findViewById(R.id.reg_signup);
        btnLogin = findViewById(R.id.reg_signup2);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformAuth();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),login.class));
            }
        });

    }
    private void PerformAuth() {
        String name = inputName.getText().toString();
        String phoneNumber = inputPhoneNumber.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputConfirmPassword.getText().toString();

        //validation
        if (name.isEmpty() || name.length() < 4) {
            //inputName.setError("Your User Name is not valid !!")
            //return;
            showError(inputName, "Your User Name is not valid !!");
        } else if (phoneNumber.isEmpty() || phoneNumber.length() > 10) {
            showError(inputPhoneNumber, "Phone Number is not valid !!");
        } else if (password.isEmpty() || password.length() > 7) {
            showError(inputPassword, "Password must be 6 character");
        } else if (confirmPassword.isEmpty() || !confirmPassword.equals(password)) {
            showError(inputConfirmPassword, "Password mismatch !!");
        }

        Toast.makeText(Register.this, "Successfully added", Toast.LENGTH_SHORT).show();
        mAuth.createUserWithEmailAndPassword(name, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                startActivity(new Intent(getApplicationContext(),Home.class));
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        return;

    }
}