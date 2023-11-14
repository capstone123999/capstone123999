package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static android.content.ContentValues.TAG;

public class TeenagerNotify extends AppCompatActivity {

    EditText teenagerNotifyTitle, teenagerNotifyReason;
    Button teenagerNotifyButton;
    String teenagerNotifyTitleResult, teenagerNotifyReasonResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lender_notify);

        teenagerNotifyTitle = findViewById(R.id.notifyTitle);
        teenagerNotifyReason = findViewById(R.id.notifyReason);
        teenagerNotifyButton = findViewById(R.id.notifyButton);




        teenagerNotifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //실평수 부분
                try {
                    teenagerNotifyTitleResult = teenagerNotifyTitle.getText().toString();
                    if(teenagerNotifyTitleResult.length()==0){
                        Toast.makeText(getApplicationContext(),"빈 칸 없이 입력해주세요",Toast.LENGTH_LONG).show();
                        return;
                    }
                }catch (NumberFormatException e){
                    Toast.makeText(TeenagerNotify.this, "신고제목을 공백 없이 입력해주세요", Toast.LENGTH_SHORT).show();
                }

                //상세 주소 부분
                try {
                    teenagerNotifyReasonResult = teenagerNotifyReason.getText().toString();
                    if(teenagerNotifyReasonResult.length()==0){
                        Toast.makeText(getApplicationContext(),"빈 칸 없이 입력해주세요",Toast.LENGTH_LONG).show();
                        return;
                    }
                }catch (NumberFormatException e){
                    Toast.makeText(TeenagerNotify.this, "신고 사유를 써주세요", Toast.LENGTH_SHORT).show();
                }
                //정보 db에 저장
                saveTeenagerNotify();

                //성공적 저장 토스트 출력 후 TeenagerMain 화면으로 연결
                Toast.makeText(TeenagerNotify.this, "신고가 접수되었습니다", Toast.LENGTH_SHORT).show();
                Intent fromTeenagerNotifyToTeenagerMain = new Intent(TeenagerNotify.this, TeenagerMainActivity.class);
                startActivity(fromTeenagerNotifyToTeenagerMain);
            }
        });
    }

    public void saveTeenagerNotify(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> teenagerNotify = new HashMap<>();
        teenagerNotify.put("TeenagerNotifyTitle", teenagerNotifyTitleResult);
        teenagerNotify.put("TeenagerNotifyText", teenagerNotifyReasonResult);

        // Add a new document with a generated ID
        db.collection("teenagerNotify")
                .add(teenagerNotify)
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