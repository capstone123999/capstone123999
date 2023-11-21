package com.example.capstone;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TeenagerMyPage extends AppCompatActivity {

    Button deleteButton,accountButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teenager_mypage);

        deleteButton = findViewById(R.id.deleteButton);
        accountButton = findViewById(R.id.accountButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TeenagerMyPage.this);
                builder.setTitle("탈퇴")
                        .setMessage("정말로 탈퇴하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 확인 버튼을 눌렀을 때 수행할 동작
                                // 원하는 동작을 수행하도록 코드를 추가하세요
                                Toast.makeText(TeenagerMyPage.this, "탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 취소 버튼을 눌렀을 때 수행할 동작
                                // 원하는 동작을 수행하도록 코드를 추가하세요
                                Toast.makeText(TeenagerMyPage.this, "취소 버튼을 눌렀습니다", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });


        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeenagerMyPage.this, AccountInfoActivity.class);
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
                        Intent findNestIntent = new Intent(TeenagerMyPage.this, FindNest.class);
                        startActivity(findNestIntent);
                        item.setChecked(false);
                        return true;
                    case R.id.action_Notify:
                        // 일반신고 메뉴 아이템 클릭 시 처리할 로직 작성
                        Intent notifyIntent = new Intent(TeenagerMyPage.this, TeenagerNotify.class);
                        startActivity(notifyIntent);
                        item.setChecked(false);
                        return true;
                    case R.id.action_Emergency:
                        // 긴급신고 메뉴 아이템 클릭 시 처리할 로직 작성
                        Intent emergencyIntent = new Intent(TeenagerMyPage.this, Emergency.class);
                        startActivity(emergencyIntent);
                        item.setChecked(false);
                        return true;
                }
                return false;
            }
        });
    }
}