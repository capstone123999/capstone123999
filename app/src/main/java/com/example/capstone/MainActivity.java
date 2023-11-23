package com.example.capstone;

import static com.example.capstone.UserChoiceInformation.usertype;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import java.sql.CallableStatement;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private SharedPreferences sharedPreferences;
    Button loginButton, joinButton, PwFindButton;
    EditText loginId, loginPw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseFirestore.getInstance();
        loginId = findViewById(R.id.loginId);
        loginPw = findViewById(R.id.loginPw);
        PwFindButton = findViewById(R.id.PwFindButton);
        loginButton = findViewById(R.id.loginButton);
        joinButton = findViewById(R.id.joinButton);

        sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(MainActivity.this, UserChoiceInformation.class);
                startActivity(intent3);
            }
        });

        loginPw.setTransformationMethod(new PasswordTransformationMethod());
        // 패스워드 입력란의 텍스트 변화를 감지하는 TextWatcher
        TextWatcher passwordTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String password = s.toString();
                String maskedPassword = "";
                for (int i = 0; i < password.length(); i++) {
                    maskedPassword += "*";
                }
                loginPw.removeTextChangedListener(this);
                loginPw.setText(maskedPassword);
                loginPw.setSelection(maskedPassword.length());
                loginPw.addTextChangedListener(this);
            }
        };

        loginPw.setTransformationMethod(new PasswordTransformationMethod());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = loginId.getText().toString();
                String password = loginPw.getText().toString();

                // Firestore에서 사용자 정보 확인
                db.collection("userInformation")
                        .whereEqualTo("id", id)
                        .whereEqualTo("passwd", password)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    QuerySnapshot querySnapshot = task.getResult();
                                    if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                        // 로그인 성공
                                        Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                                        // 로그인 로직 구현...
                                        // 로그인 성공 시 사용자 ID를 SharedPreferences에 저장
                                        getSharedPreferences("userPrefs", MODE_PRIVATE).edit().putString("id", id).apply();
                                        // 사용자 유형에 따라 다른 액티비티로 이동
                                        String userType = querySnapshot.getDocuments().get(0).getString("usertype");
                                        if (userType != null) {
                                            if (userType.equals("청소년")) {
                                                Intent intent = new Intent(MainActivity.this, TeenagerMainActivity.class);
                                                startActivity(intent);
                                            } else if (userType.equals("대여자")) {
                                                Intent intent = new Intent(MainActivity.this, LenderMainActivity.class);
                                                startActivity(intent);
                                            } else if (userType.equals("관리자")) {
                                                Intent intent = new Intent(MainActivity.this, ManagerMainActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    } else {
                                        // 로그인 실패
                                        Toast.makeText(MainActivity.this, "아이디 또는 비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // Firestore에서 데이터 가져오기 실패
                                    Toast.makeText(MainActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        PwFindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, passwdFindActivity.class);
                startActivity(intent);

            }
        });

    }
}