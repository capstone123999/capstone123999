package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ManagerNotify extends AppCompatActivity {

    Button manager_notify_teenager_button, manager_notify_lender_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_notify);

        manager_notify_teenager_button = findViewById(R.id.manager_notify_teenager_button);
        manager_notify_lender_button = findViewById(R.id.manager_notify_lender_button);

        manager_notify_teenager_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerNotify.this, ManagerNotifyTeenager.class);
                startActivity(intent);
            }
        });

        manager_notify_lender_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerNotify.this, ManagerNotifyLender.class);
                startActivity(intent);
            }
        });
    }
}
