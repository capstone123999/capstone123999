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
        Button Emergency = findViewById(R.id.Emergency);
        Emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeenagerMainActivity.this, Emergency.class);
                startActivity(intent);
            }
        });

        // TeenagerNotify 버튼 클릭 시 이벤트 처리
        Button teenagerMainNotify = findViewById(R.id.teenagerMainNotify);
        teenagerMainNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeenagerMainActivity.this, TeenagerNotify.class);
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

        // LenderMyPage 이미지 버튼 클릭 시 이벤트 처리
        ImageButton UserImage = findViewById(R.id.UserImage);
        UserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeenagerMainActivity.this, TeenagerMyPage.class);
                startActivity(intent);

            }
        });
    }
}
