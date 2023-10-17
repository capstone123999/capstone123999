package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_choice);

        //청소년 버튼 클릭 시 이벤트 처리
        Button TeenagerButton = findViewById(R.id.TeenagerButton);
        TeenagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TeenagerMainActivity.class);
                startActivity(intent);
            }
        });

        // 대여자 버튼 클릭 시 이벤트 처리
        Button LenderButton = findViewById(R.id.LenderButton);
        LenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LenderMainActivity.class);
                startActivity(intent);
            }
        });
    }
}