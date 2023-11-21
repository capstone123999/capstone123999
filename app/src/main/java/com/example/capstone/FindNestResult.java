package com.example.capstone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FindNestResult extends AppCompatActivity {

    Button findSuccessButton, streetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest_find_result);

        findSuccessButton = findViewById(R.id.findSuccessButton);
        streetButton = findViewById(R.id.streetButton);

        streetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindNestResult.this, MapActivity.class);
                startActivity(intent);
            }
        });

        findSuccessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                // Firestore에서 청소년의 거주 기간과 대여자의 대여 기간 가져오기
                db.collection("userInformation")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    QuerySnapshot querySnapshot = task.getResult();
                                    if (querySnapshot != null) {
                                        List<DocumentSnapshot> documents = querySnapshot.getDocuments();
                                        List<DocumentSnapshot> teenagers = new ArrayList<>();
                                        List<DocumentSnapshot> renters = new ArrayList<>();

                                        // 청소년과 대여자 구분하여 리스트에 저장
                                        for (DocumentSnapshot document : documents) {
                                            String userType = document.getString("usertype");
                                            if ("청소년".equals(userType)) {
                                                teenagers.add(document);
                                            } else if ("대여자".equals(userType)) {
                                                renters.add(document);
                                            }
                                        }

                                        // 랜덤으로 청소년과 대여자 선택
                                        Random random = new Random();
                                        DocumentSnapshot randomTeenager = teenagers.get(random.nextInt(teenagers.size()));
                                        DocumentSnapshot randomRenter = renters.get(random.nextInt(renters.size()));

                                        // 거주 기간과 대여 기간 비교
                                        String residentPeriod = randomTeenager.getString("residentPeriod");
                                        String rentalPeriod = randomRenter.getString("rentalPeriod");
                                        if (residentPeriod != null && rentalPeriod != null && residentPeriod.compareTo(rentalPeriod) <= 0) {
                                            Toast.makeText(FindNestResult.this, "배치 성공", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(FindNestResult.this, "배치 실패", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } else {
                                    Toast.makeText(FindNestResult.this, "데이터 가져오기 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}

