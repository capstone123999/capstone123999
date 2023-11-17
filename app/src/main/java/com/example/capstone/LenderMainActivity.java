package com.example.capstone;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        // Notify 버튼 클릭 시 이벤트 처리
        Button lenderMainNotify = findViewById(R.id.lenderMainNotify);
        lenderMainNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LenderMainActivity.this, LenderNotify.class);
                startActivity(intent);
            }
        });
        // MyPage 이미지 버튼 클릭 시 이벤트 처리
        ImageButton UserImage = findViewById(R.id.UserImage);
        UserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LenderMainActivity.this, LenderMyPage.class);
                startActivity(intent);

            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        ColorStateList iconColor = ContextCompat.getColorStateList(this, R.color.custom_icon_color);
        bottomNavigationView.setItemIconTintList(iconColor);

        ColorStateList textColor = ContextCompat.getColorStateList(this, R.color.custom_text_color);
        bottomNavigationView.setItemTextColor(textColor);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_Nest:
                        // 보금자리 등록 메뉴 아이템 클릭 시 처리할 로직 작성
                        Intent SubmitNestIntent = new Intent(LenderMainActivity.this, SubmitNest.class);
                        startActivity(SubmitNestIntent);
                        return true;
                    case R.id.action_Notify:
                        // 일반신고 메뉴 아이템 클릭 시 처리할 로직 작성
                        Intent notifyIntent = new Intent(LenderMainActivity.this, LenderNotify.class);
                        startActivity(notifyIntent);
                        return true;
                    case R.id.action_Emergency:
                        // 긴급신고 메뉴 아이템 클릭 시 처리할 로직 작성
                        Intent emergencyIntent = new Intent(LenderMainActivity.this, Emergency.class);
                        startActivity(emergencyIntent);
                        return true;
                }
                return false;
            }
        });
    }
}
