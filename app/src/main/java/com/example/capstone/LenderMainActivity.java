package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LenderMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lender_main);

        // lenderMainNestSubmit 버튼 클릭 시 이벤트 처리
        Button lenderMainNestSubmit = findViewById(R.id.lenderMainNestSubmit);
        lenderMainNestSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LenderMainActivity.this, SubmitNest.class);
                startActivity(intent);
            }
        });
        // Emergency 버튼 클릭 시 이벤트 처리
        Button lenderMainEmergency = findViewById(R.id.lenderMainEmergency);
        lenderMainEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LenderMainActivity.this, Emergency.class);
                startActivity(intent);
            }
        });

        // Notify 버튼 클릭 시 이벤트 처리
        Button lenderMainNotify = findViewById(R.id.lenderMainNotify);
        lenderMainNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LenderMainActivity.this, Notify.class);
                startActivity(intent);
            }
        });
    }
}
