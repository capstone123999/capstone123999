package com.example.capstone;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FindNestResult extends AppCompatActivity {

    Button findSuccessButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest_find_result);

        findSuccessButton = findViewById(R.id.findSuccessButton);

        findSuccessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                // Firestore에서 청소년의 거주 기간과 대여자의 대여 기간 가져오기

                CollectionReference findNestCollection = db.collection("findNest");
                CollectionReference submitNestCollection = db.collection("submitNest");

                findNestCollection.get().addOnCompleteListener(findNestTask -> {
                    if (findNestTask.isSuccessful()) {
                        for (QueryDocumentSnapshot findNestDocument : findNestTask.getResult()) {
                            String findNestGender = findNestDocument.getString("teenagerGender");
                            String findNestResidentPeriod = findNestDocument.getString("residentPeriod");
                            String residentPeriodNumeric = findNestResidentPeriod.replaceAll("[^\\d.]", "");

                            if (findNestGender == null || findNestResidentPeriod == null) {
                                // 청소년 정보가 null인 경우의 처리
                                Toast.makeText(FindNestResult.this, "청소년 정보를 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            submitNestCollection.get().addOnCompleteListener(submitNestTask -> {
                                if (submitNestTask.isSuccessful()) {
                                    for (QueryDocumentSnapshot submitNestDocument : submitNestTask.getResult()) {
                                        String submitNestGender = submitNestDocument.getString("lenderGender");
                                        String submitNestRentalPeriod = submitNestDocument.getString("rental period");
                                        String submitNestAddress = submitNestDocument.getString("address");
                                        String rentalPeriodNumeric = submitNestRentalPeriod.replaceAll("[^\\d.]", "");


                                        if (submitNestGender == null || submitNestRentalPeriod == null) {
                                            // 대여자 정보가 null인 경우의 처리
                                            Toast.makeText(FindNestResult.this, "대여자 정보를 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        if (!findNestGender.equals(submitNestGender)) {
                                            // 배치 실패 팝업창 띄우기
                                            AlertDialog.Builder builder = new AlertDialog.Builder(FindNestResult.this);
                                            builder.setTitle("")
                                                    .setMessage("배치 실패")
                                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // 확인 버튼을 눌렀을 때 수행할 동작
                                                            Toast.makeText(FindNestResult.this, "메인화면으로 돌아갑니다.", Toast.LENGTH_SHORT).show();
                                                            Intent intent2 = new Intent(FindNestResult.this, TeenagerMainActivity.class);
                                                            startActivity(intent2);
                                                        }
                                                    })
                                                    .show();
                                        } else if (Integer.parseInt(rentalPeriodNumeric) < Integer.parseInt(residentPeriodNumeric)) {
                                            // 그래도 거주하시겠습니까 팝업창 띄우기
                                            AlertDialog.Builder builder = new AlertDialog.Builder(FindNestResult.this);
                                            builder.setTitle("")
                                                    .setMessage("대여기간이 " + submitNestRentalPeriod + "입니다. 그래도 거주하시겠습니까?")
                                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // 확인 버튼을 눌렀을 때 수행할 동작
                                                            Toast.makeText(FindNestResult.this, "안내 화면으로 넘어갑니다.", Toast.LENGTH_SHORT).show();
                                                            Intent intent2 = new Intent(FindNestResult.this, MapActivity.class);
                                                            startActivity(intent2);

                                                            String nestResultUserId = getSharedPreferences("userPrefs", MODE_PRIVATE).getString("id", null);

                                                            if (nestResultUserId != null && !nestResultUserId.isEmpty()) {
                                                                db.collection("userInformation")
                                                                        .whereEqualTo("id", nestResultUserId)
                                                                        .get()
                                                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                if (task.isSuccessful()) {
                                                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                        // Firestore에서 필드 값을 가져와서 변수에 저장
                                                                                        String name = document.getString("name");
                                                                                        String gender = document.getString("gender");
                                                                                        String id = document.getString("id");

                                                                                        // Write a message to the database
                                                                                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                                                                                        Map<String, Object> nestResult = new HashMap<>();
                                                                                        nestResult.put("teenagerId", id);
                                                                                        nestResult.put("teenagerGender", gender);
                                                                                        nestResult.put("teenagerName", name);
                                                                                        nestResult.put("address", submitNestAddress);
                                                                                        // Add a new document with a generated ID
                                                                                        db.collection("nestResult")
                                                                                                .add(nestResult)
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
                                                                                } else {
                                                                                    Log.w(TAG, "Error getting documents.", task.getException());
                                                                                }
                                                                            }
                                                                        });
                                                            } else {
                                                                Log.d(TAG, "SharedPreferences 'id' is null or empty");
                                                                // Handle this case as needed
                                                            }

                                                        }
                                                    })
                                                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // 취소 버튼을 눌렀을 때 수행할 동작
                                                            // 원하는 동작을 수행하도록 코드를 추가하세요
                                                            Toast.makeText(FindNestResult.this, "취소 버튼을 눌렀습니다", Toast.LENGTH_SHORT).show();
                                                            Intent intent2 = new Intent(FindNestResult.this, TeenagerMainActivity.class);
                                                            startActivity(intent2);
                                                        }
                                                    })
                                                    .show();
                                        } else {
                                            // 배치 성공 팝업창 띄우기
                                            AlertDialog.Builder builder = new AlertDialog.Builder(FindNestResult.this);
                                            builder.setTitle("")
                                                    .setMessage("배치가 완료되었습니다.")
                                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // 확인 버튼을 눌렀을 때 수행할 동작
                                                            Toast.makeText(FindNestResult.this, "안내 화면으로 넘어갑니다.", Toast.LENGTH_SHORT).show();
                                                            Intent intent2 = new Intent(FindNestResult.this, MapActivity.class);
                                                            startActivity(intent2);

                                                            String nestResultUserId = getSharedPreferences("userPrefs", MODE_PRIVATE).getString("id", null);

                                                            if (nestResultUserId != null && !nestResultUserId.isEmpty()) {
                                                                db.collection("userInformation")
                                                                        .whereEqualTo("id", nestResultUserId)
                                                                        .get()
                                                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                if (task.isSuccessful()) {
                                                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                        // Firestore에서 필드 값을 가져와서 변수에 저장
                                                                                        String name = document.getString("name");
                                                                                        String gender = document.getString("gender");
                                                                                        String id = document.getString("id");

                                                                                        // Write a message to the database
                                                                                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                                                                                        Map<String, Object> nestResult = new HashMap<>();
                                                                                        nestResult.put("teenagerId", id);
                                                                                        nestResult.put("teenagerGender", gender);
                                                                                        nestResult.put("teenagerName", name);
                                                                                        nestResult.put("address", submitNestAddress);
                                                                                        // Add a new document with a generated ID
                                                                                        db.collection("nestResult")
                                                                                                .add(nestResult)
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
                                                                                } else {
                                                                                    Log.w(TAG, "Error getting documents.", task.getException());
                                                                                }
                                                                            }
                                                                        });
                                                            } else {
                                                                Log.d(TAG, "SharedPreferences 'id' is null or empty");
                                                                // Handle this case as needed
                                                            }

                                                        }
                                                    })
                                                    .show();
                                        }
                                    }
                                }
                            });
                        }
                    }
                });

            }
        });

    }
}