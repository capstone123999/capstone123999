package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ManagerMainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);

        Button manager_button1 = findViewById(R.id.manager_button1);
        Button manager_button2 = findViewById(R.id.manager_button2);
        Button manager_button3 = findViewById(R.id.manager_button3);
        Button manager_button4 = findViewById(R.id.manager_button4);

        manager_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerMainActivity.this, ManagerNestSubmitActivity.class);
                startActivity(intent);
            }
        });
        manager_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerMainActivity.this, ManagerNestMatchResultActivity.class);
                startActivity(intent);
            }
        });
        manager_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerMainActivity.this, ManagerNotify.class);
                startActivity(intent);
            }
        });
        manager_button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerMainActivity.this, ManagerEmergency.class);
                startActivity(intent);
            }
        });

    }
}
