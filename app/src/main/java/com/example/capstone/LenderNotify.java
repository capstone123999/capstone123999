package com.example.capstone;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import static android.content.ContentValues.TAG;

public class LenderNotify extends AppCompatActivity {

    EditText lenderNotifyTitle, LenderNotifyReason;
    Button LenderNotifyButton;
    String LenderNotifyTitleResult, LenderNotifyReasonResult, lenderNotifyCategory;
    Spinner lender_notify_select;
    ArrayAdapter<String> adapter1;
    String[] items = {"신고 사유 : 항목 중 선택", "금전 갈취", "기물 파손","청소년의 인성적 결함","기타"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lender_notify);

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
                        Intent findNestIntent = new Intent(LenderNotify.this, SubmitNest.class);
                        startActivity(findNestIntent);
                        item.setChecked(false);
                        return true;
                    case R.id.action_Notify:
                        // 일반신고 메뉴 아이템 클릭 시 처리할 로직 작성
                        Intent notifyIntent = new Intent(LenderNotify.this, LenderNotify.class);
                        startActivity(notifyIntent);
                        item.setChecked(true);
                        return true;
                    case R.id.action_Emergency:
                        // 긴급신고 메뉴 아이템 클릭 시 처리할 로직 작성
                        Intent emergencyIntent = new Intent(LenderNotify.this, Emergency.class);
                        startActivity(emergencyIntent);
                        item.setChecked(false);
                        return true;
                }
                return true;
            }
        });

        lenderNotifyTitle = findViewById(R.id.notifyTitle);
        LenderNotifyReason = findViewById(R.id.notifyReason);
        LenderNotifyButton = findViewById(R.id.notifyButton);
        lender_notify_select = findViewById(R.id.lender_notify_select);

        lenderNotifyTitle.setVisibility(View.GONE);

        //개월 수 선택 스피너
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        lender_notify_select =findViewById(R.id.lender_notify_select);
        lender_notify_select.setAdapter(adapter1);
        lender_notify_select.setSelection(0);
        lender_notify_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                Intent lenderNotifyCategoryIntent = new Intent();
                lenderNotifyCategory =adapter1.getItem(i);
                lenderNotifyCategoryIntent.putExtra("lenderNotifyCategory", lenderNotifyCategory);

                if(lenderNotifyCategory.equals("기타")){
                    lenderNotifyTitle.setVisibility(View.VISIBLE);
                }else{
                    lenderNotifyTitle.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        LenderNotifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //실평수 부분
                try {
                    LenderNotifyTitleResult = lenderNotifyTitle.getText().toString();
                    if(LenderNotifyTitleResult.length()==0 && lenderNotifyCategory.equals("신고 사유 : 항목 중 선택")){
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
        LenderNotify.put("lenderNotifyCategory", lenderNotifyCategory);

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