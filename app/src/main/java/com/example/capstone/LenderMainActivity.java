package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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
        Button Emergency = findViewById(R.id.Emergency);
        Emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LenderMainActivity.this, Emergency.class);
                startActivity(intent);
            }
        });

        // LenderNotify 버튼 클릭 시 이벤트 처리
        Button lenderMainNotify = findViewById(R.id.lenderMainNotify);
        lenderMainNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LenderMainActivity.this, LenderNotify.class);
                startActivity(intent);
            }
        });
        // LenderMyPage 이미지 버튼 클릭 시 이벤트 처리
        ImageButton UserImage = findViewById(R.id.UserImage);
        UserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LenderMainActivity.this, LenderMyPage.class);
                startActivity(intent);

            }
        });
    }
}
