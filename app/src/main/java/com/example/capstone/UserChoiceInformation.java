package com.example.capstone;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserChoiceInformation extends AppCompatActivity {
    public static String usertype;
    Button TeenagerButton, LenderButton, ManagerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_choice_information);

        TeenagerButton = findViewById(R.id.TeenagerButton);
        LenderButton = findViewById(R.id.LenderButton);
        ManagerButton = findViewById(R.id.ManagerButton);

        TeenagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usertype = "청소년";
                Intent intent5 = new Intent(UserChoiceInformation.this, JoinActivity.class);
                startActivity(intent5);
                // 다음 화면으로 이동
            }
        });
        LenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usertype = "대여자";
                Intent intent5 = new Intent(UserChoiceInformation.this, JoinActivity.class);
                startActivity(intent5);
                // 다음 화면으로 이동
            }
        });
        ManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usertype = "관리자";
                Intent intent5 = new Intent(UserChoiceInformation.this, JoinActivity.class);
                startActivity(intent5);
            }
        });

    }
}

/*

    String userType;
    Button TeenagerCheckButton, LenderCheckButton, ManagerCheckButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_choice_information);

        TeenagerCheckButton = findViewById(R.id.TeenagerCheckButton);
        LenderCheckButton = findViewById(R.id.LenderCheckButton);
        ManagerCheckButton = findViewById(R.id.ManagerCheckButton);

        TeenagerCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToFirebase("청소년");
                Intent intent5 = new Intent(UserChoiceInformation.this, JoinActivity.class);
                startActivity(intent5);
            }
        });

        LenderCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToFirebase("대여자");
                Intent intent5 = new Intent(UserChoiceInformation.this, JoinActivity.class);
                startActivity(intent5);
            }
        });

        ManagerCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToFirebase("관리자");
                Intent intent5 = new Intent(UserChoiceInformation.this, JoinActivity.class);
                startActivity(intent5);
            }

        });
    }


    private void saveDataToFirebase(String role) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> userInformation = new HashMap<>();
        userInformation.put("userType", role);

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
*/