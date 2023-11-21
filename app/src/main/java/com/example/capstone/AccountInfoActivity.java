package com.example.capstone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static android.content.ContentValues.TAG;

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

        //장채원 작성
        db.collection("userInformation")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
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

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}