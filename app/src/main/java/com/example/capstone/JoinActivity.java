package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.text.BreakIterator;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class JoinActivity extends AppCompatActivity {
    Spinner genderChoice;

    String[] joinItems;
    public static String joinName;
    public static String genderChoiceResult;
    String residentNum1;
    String residentNum2;
    String rasidentNumber;
    public static String login_ID;
    public static String login_pw;

    EditText name, num1, num2, number, joinId, joinPw;
    Button numberButton, joinRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        // xml 파일에서 선언한 아이디 연결
        name = findViewById(R.id.name);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        number = findViewById(R.id.number);
        joinId = findViewById(R.id.joinId);
        joinPw = findViewById(R.id.joinPw);

        numberButton = findViewById(R.id.numberButton);
        joinRegisterButton = findViewById(R.id.joinRegisterButton);

        genderChoice = findViewById(R.id.genderChoice);

        // 스피너 아이템 설정
        joinItems = new String[]{"선택", "남", "여"};

        // 스피너 초기화 및 어댑터 설정
        genderChoice = findViewById(R.id.genderChoice);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, joinItems);

        genderChoice.setAdapter(adapter1);
        genderChoice.setSelection(0);
        // 스피너 아이템 선택 이벤트 처리
        genderChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,  int position, long id) {
                Intent genderIntent = new Intent();
                genderChoiceResult=adapter1.getItem(position);
                genderIntent.putExtra("genderIntent1", genderChoiceResult);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택되지 않았을 때의 처리
            }
        });


        Button joinRegisterButton = findViewById(R.id.joinRegisterButton);
        joinRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinName = name.getText().toString();

                if (joinName.isEmpty()) {
                    Toast.makeText(JoinActivity.this, "이름을 공백 없이 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                residentNum1 = num1.getText().toString();

                if (residentNum1.isEmpty()) {
                    Toast.makeText(JoinActivity.this, "주민번호를 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                residentNum2 = num2.getText().toString();

                if (residentNum2.isEmpty()) {
                    Toast.makeText(JoinActivity.this, "전화번호를 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                rasidentNumber = number.getText().toString();

                if (rasidentNumber.isEmpty()) {
                    Toast.makeText(JoinActivity.this, "아이디를 공백없이 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                login_ID = joinId.getText().toString();

                if (login_ID.isEmpty()) {
                    Toast.makeText(JoinActivity.this, "아이디를 공백없이 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                login_pw = joinPw.getText().toString();

                if (login_pw.isEmpty()) {
                    Toast.makeText(JoinActivity.this, "비밀번호를 공백없이 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                //정보 db에 저장
                saveJoinActivity();
                Toast.makeText(JoinActivity.this, "사용자 정보가 저장되었습니다", Toast.LENGTH_SHORT).show();
                Intent fromJoinActivityToLogin = new Intent(JoinActivity.this, MainActivity.class);
                startActivity(fromJoinActivityToLogin);
            }
        });

    }

    public void saveJoinActivity() {
        // Write a message to the database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> userInformation = new HashMap<>();
        userInformation.put("name", joinName);
        userInformation.put("gender", genderChoiceResult);
        userInformation.put("residentNum1", residentNum1);
        userInformation.put("residentNum2", residentNum2);
        userInformation.put("number", rasidentNumber);
        userInformation.put("id",login_ID);
        userInformation.put("passwd",login_pw);
        userInformation.put("usertype",UserChoiceInformation.usertype);

        // Add a new document with a generated ID
        db.collection("userInformation")
                .add(userInformation)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
