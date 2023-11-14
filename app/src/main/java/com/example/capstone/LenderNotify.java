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

public class LenderNotify extends AppCompatActivity {

    EditText lenderNotifyTitle, LenderNotifyReason;
    Button LenderNotifyButton;
    String LenderNotifyTitleResult, LenderNotifyReasonResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lender_notify);

        lenderNotifyTitle = findViewById(R.id.notifyTitle);
        LenderNotifyReason = findViewById(R.id.notifyReason);
        LenderNotifyButton = findViewById(R.id.notifyButton);




        LenderNotifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //실평수 부분
                try {
                    LenderNotifyTitleResult = lenderNotifyTitle.getText().toString();
                    if(LenderNotifyTitleResult.length()==0){
                        Toast.makeText(getApplicationContext(),"빈 칸 없이 입력해주세요",Toast.LENGTH_LONG).show();
                        return;
                    }
                }catch (NumberFormatException e){
                    Toast.makeText(LenderNotify.this, "신고제목을 공백 없이 입력해주세요", Toast.LENGTH_SHORT).show();
                }

                //상세 주소 부분
                try {
                    LenderNotifyReasonResult = LenderNotifyReason.getText().toString();
                    if(LenderNotifyReasonResult.length()==0){
                        Toast.makeText(getApplicationContext(),"빈 칸 없이 입력해주세요",Toast.LENGTH_LONG).show();
                        return;
                    }
                }catch (NumberFormatException e){
                    Toast.makeText(LenderNotify.this, "신고 사유를 써주세요", Toast.LENGTH_SHORT).show();
                }
                //정보 db에 저장
                saveLenderNotify();

                //성공적 저장 토스트 출력 후 LenderMain 화면으로 연결
                Toast.makeText(LenderNotify.this, "신고가 접수되었습니다", Toast.LENGTH_SHORT).show();
                Intent fromLenderNotifyToLenderMain = new Intent(LenderNotify.this, LenderMainActivity.class);
                startActivity(fromLenderNotifyToLenderMain);
            }
        });
    }

    public void saveLenderNotify(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> LenderNotify = new HashMap<>();
        LenderNotify.put("LenderNotifyTitle", LenderNotifyTitleResult);
        LenderNotify.put("LenderNotifyText", LenderNotifyReasonResult);

        // Add a new document with a generated ID
        db.collection("LenderNotify")
                .add(LenderNotify)
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