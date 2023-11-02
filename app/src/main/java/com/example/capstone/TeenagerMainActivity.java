package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class TeenagerMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teenager_main);

        // Emergency 버튼 클릭 시 이벤트 처리
        Button teenagerMainEmergency = findViewById(R.id.teenagerMainEmergency);
        teenagerMainEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeenagerMainActivity.this, Emergency.class);
                startActivity(intent);
            }
        });

        // Notify 버튼 클릭 시 이벤트 처리
        Button teenagerMainNotify = findViewById(R.id.teenagerMainNotify);
        teenagerMainNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeenagerMainActivity.this, Notify.class);
                startActivity(intent);
            }
        });

        // FindNest 버튼 클릭 시 이벤트 처리
        Button teenagerMainNestFind = findViewById(R.id.teenagerMainNestFind);
        teenagerMainNestFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeenagerMainActivity.this, FindNest.class);
                startActivity(intent);
            }
        });

        // MyPage 이미지 버튼 클릭 시 이벤트 처리
        ImageButton teenagerMainMyPageImage = findViewById(R.id.UserImage);
        teenagerMainMyPageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeenagerMainActivity.this, MyPage.class);
                startActivity(intent);

            }
        });
    }
}
