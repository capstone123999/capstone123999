package com.example.capstone;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import static android.content.ContentValues.TAG;
import java.util.HashMap;
import java.util.Map;

/***
 * '보금자리 찾기' 기능.
 * 1.회원가입에서 받은 개인정보를 중 필요한 정보를 갖고와서 매칭에 사용한다.
 * 2.매칭 완료되면 '매칭 완료(find_nest_result)'화면을 보여준 뒤 <확인> 버튼을 누르면 ①경찰에게 알림이 가고 ②청소년에겐 가까운 파출소 위
 * 치를 안내해준다.
 */
public class FindNest extends AppCompatActivity {
    String monthCheckChoice;
    Spinner monthChoice;
    String[] items1;
    Button nestFindRegisterButton;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest_find);

        // 스피너 아이템 설정
        items1 = new String[]{"1개월", "2개월", "3개월", "4개월", "5개월", "6개월", "7개월", "8개월", "9개월", "10개월", "11개월", "12개월"};

        // 스피너 초기화 및 어댑터 설정
        monthChoice = findViewById(R.id.monthChoice);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);

        monthChoice.setAdapter(adapter1);
        db = FirebaseFirestore.getInstance();

        // 스피너 아이템 선택 이벤트 처리
        monthChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Intent monthIntent = new Intent();
                monthCheckChoice = adapter1.getItem(position);
                monthIntent.putExtra("genderIntent1", monthCheckChoice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택되지 않았을 때의 처리
            }
        });

        nestFindRegisterButton = findViewById(R.id.nestFindRegisterButton);
        nestFindRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //정보 db에 저장
                saveFindNest();
                //성공적 저장 토스트 출력 후 LenderMain 화면으로 연결
                Intent fromFindNestToMap = new Intent(FindNest.this, FindNestResult.class);
                startActivity(fromFindNestToMap);
            }
        });
    }

    public void saveFindNest() {
        String findNestUserId = getSharedPreferences("userPrefs", MODE_PRIVATE).getString("id", null);

        if (findNestUserId != null && !findNestUserId.isEmpty()) {
            db.collection("userInformation")
                    .whereEqualTo("id", findNestUserId)
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
                                    String usertype= document.getString("usertype");

                                    // Write a message to the database
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    Map<String, Object> findNest = new HashMap<>();
                                    findNest.put("residentPeriod", monthCheckChoice);
                                    findNest.put("teenagerId", id);
                                    findNest.put("teenagerGender", gender);
                                    findNest.put("teenagerName", name);
                                    findNest.put("usertype", usertype);

                                    // Add a new document with a generated ID
                                    db.collection("findNest")
                                            .add(findNest)
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
}
