package com.example.capstone;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class passwdFindActivity extends AppCompatActivity {

    private TextView rePasswordResult;

    private FirebaseFirestore db;
    Button passwdFindButton, backButton;
    EditText rePassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwdfind);

        db = FirebaseFirestore.getInstance();

        rePassword = findViewById(R.id.rePassword); // 여기서 'editText'는 사용자가 입력한 아이디를 받아오는 EditText의 ID입니다.
        passwdFindButton= findViewById(R.id.passwdFindButton); // 여기서 'button'은 버튼의 ID입니다.
        rePasswordResult= findViewById(R.id.rePasswordResult);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        passwdFindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserId = rePassword.getText().toString().trim();
                if (UserId != null && !UserId.isEmpty()) {
                    db.collection("userInformation")
                            .whereEqualTo("id", UserId)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            // Firestore에서 필드 값을 가져와서 변수에 저장
                                            String pw3 = document.getString("passwd");
                                            // 화면에 회원가입 정보를 보여줌
                                            rePasswordResult.setText(pw3);
                                        }
                                    } else {
                                        Toast.makeText(passwdFindActivity.this, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT).show();
                                        Log.w(TAG, "Error getting documents.", task.getException());
                                    }
                                }
                            });
                } else {
                    Log.d(TAG, "SharedPreferences 'id' is null or empty");
                    // Handle this case as needed
                }

            }
        });

    }

}
