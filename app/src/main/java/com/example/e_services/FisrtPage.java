package com.example.e_services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FisrtPage extends AppCompatActivity {
    Button reg_user,reg_worker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fisrt_page);
        reg_user=findViewById(R.id.register_as_user);
        reg_worker=findViewById(R.id.register_as_worker);

        reg_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FisrtPage.this , MainActivity.class);
                startActivity(i);
            }
        });

        reg_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FisrtPage.this , registration_worker.class);
                startActivity(i);
            }
        });
    }
}