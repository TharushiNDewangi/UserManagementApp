package com.example.login_registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    TextView t1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        t1 = findViewById(R.id.homeheader);


        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnback();
            }
        });
    }

    public void returnback() {
        //Toast.makeText(getApplicationContext(), "Successfully added", Toast.LENGTH_SHORT).show();

        //call second activity
        Intent intent = new Intent(this, login.class);
        //

        startActivity(intent);

    }
}