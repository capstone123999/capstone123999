package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ManagerNotifyTeenager extends AppCompatActivity {

    String teenagerNotifyText, teenagerNotifyTitle;
    ListView notify_info_teenager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_notify_teenager);

        notify_info_teenager = findViewById(R.id.notify_info_teenager);

        //DB 정보 가져오기
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        DocumentReference docRef = db.collection("teenagerNotify").document("xP2mttwY6xKBw7DaFI0v");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        teenagerNotifyText = document.getString("TeenagerNotifyText");
                        teenagerNotifyTitle = document.getString("TeenagerNotifyTitle");

                        ArrayList<CustomListView.ListData> listViewData = new ArrayList<>();

                        CustomListView.ListData listData = new CustomListView.ListData();

                        listData.mainImage = R.drawable.idicon;

                        listData.title = teenagerNotifyTitle;
                        listData.body_1 = teenagerNotifyText;

                        listViewData.add(listData);

                        ListAdapter oAdapter = new CustomListView(listViewData);
                        notify_info_teenager.setAdapter(oAdapter);

                        notify_info_teenager.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String clickName = listViewData.get(position).title;
                                Log.d("확인","name : "+clickName);
                            }
                        });
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        notify_info_teenager.setAdapter(adapter);


    }
}