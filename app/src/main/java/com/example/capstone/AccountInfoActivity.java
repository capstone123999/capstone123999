package com.example.capstone;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountInfoActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private TextView nameTextView, genderTextView, residentNum1TextView,numberTextView, idTextView, pwTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        db = FirebaseFirestore.getInstance();

        nameTextView = findViewById(R.id.nameTextView);
        genderTextView = findViewById(R.id.genderTextView);
        residentNum1TextView = findViewById(R.id.residentNum1TextView);
        numberTextView = findViewById(R.id.numberTextView);
        idTextView = findViewById(R.id.idTextView);
        pwTextView = findViewById(R.id.pwTextView);

        String id = getSharedPreferences("userPrefs", MODE_PRIVATE).getString("id", "");

        // Firestore에서 회원가입 정보 가져오기
        if (id != null && !id.isEmpty()) {
        db.collection("userInformation")
                .document(id)   // 회원의 고유한 문서 ID를 지정해야 합니다
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                String userType = document.getString("usertype");

                                // usertype이 "청소년"인 경우에만 데이터를 보여줌
                                if ("청소년".equals(userType)) {
                                    // Firestore에서 필드 값을 가져와서 변수에 저장
                                    String name = document.getString("name");
                                    String gender = document.getString("gender");
                                    String residentNum1 = document.getString("residentNum1");
                                    String number = document.getString("number");
                                    String id = document.getString("id");
                                    String pw = document.getString("passwd");

                                    // 화면에 회원가입 정보를 보여줌
                                    nameTextView.setText(name);
                                    genderTextView.setText(gender);
                                    residentNum1TextView.setText(residentNum1);
                                    numberTextView.setText(number);
                                    idTextView.setText(id);
                                    pwTextView.setText(pw);
                                } else {
                                    // 청소년이 아닌 경우 처리
                                    Toast.makeText(AccountInfoActivity.this, "청소년 회원만 계정 정보를 볼 수 있습니다.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // 문서가 존재하지 않는 경우 처리
                                Toast.makeText(AccountInfoActivity.this, "회원 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Firestore에서 데이터 가져오기 실패 처리
                            Toast.makeText(AccountInfoActivity.this, "회원 정보를 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        } else {
            Toast.makeText(AccountInfoActivity.this, "로그인한 사용자 정보가 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}