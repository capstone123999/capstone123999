package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

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
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationview);
        ColorStateList iconColor = ContextCompat.getColorStateList(this, R.color.custom_icon_color);
        bottomNavigationView.setItemIconTintList(iconColor);

        ColorStateList textColor = ContextCompat.getColorStateList(this, R.color.custom_text_color);
        bottomNavigationView.setItemTextColor(textColor);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_Nest:
                        // 보금자리 찾기 메뉴 아이템 클릭 시 처리할 로직 작성
                        Intent findNestIntent = new Intent(TeenagerMainActivity.this, FindNest.class);
                        startActivity(findNestIntent);
                        item.setChecked(false);
                        return true;
                    case R.id.action_Notify:
                        // 일반신고 메뉴 아이템 클릭 시 처리할 로직 작성
                        Intent notifyIntent = new Intent(TeenagerMainActivity.this, TeenagerNotify.class);
                        startActivity(notifyIntent);
                        item.setChecked(false);
                        return true;
                    case R.id.action_Emergency:
                        // 긴급신고 메뉴 아이템 클릭 시 처리할 로직 작성
                        Intent emergencyIntent = new Intent(TeenagerMainActivity.this, Emergency.class);
                        startActivity(emergencyIntent);
                        item.setChecked(false);
                        return true;
                }
                return false;
            }
        });
    }
}
