package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ManagerNestMatchResultActivity extends AppCompatActivity {

    private FirebaseFirestore db; // Firestore instance declaration
    private List<ListLayout3> itemList; // array of list items
    private ListAdapter3 adapter; // Recycler View Adapter
    RecyclerView manager_nest_result_recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_nest_match_result);
//장채원 작성 start
        manager_nest_result_recyclerview = findViewById(R.id.manager_nest_result_recyclerview);

        //DB 정보 가져오기
        db = FirebaseFirestore.getInstance();
        itemList = new ArrayList<>();
        adapter = new ListAdapter3((ArrayList<ListLayout3>) itemList);

        if (manager_nest_result_recyclerview != null) {
            // Set LayoutManager and Adapter for the RecyclerView
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            manager_nest_result_recyclerview.setLayoutManager(layoutManager);
            manager_nest_result_recyclerview.setAdapter(adapter);
        } else {
            // Handle the case if notify_info_lender is not found or not a RecyclerView
            Log.e("RecyclerView Error", "notify_info_lender not found or not a RecyclerView");
        }


        db.collection("nestResult") // Collection to work with
                .get() // Get document
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    // if successful
                    itemList.clear();
                    if (queryDocumentSnapshots.isEmpty()) {
                        // Handle case when there are no documents in the collection
                        // For example, you can display a message or perform specific actions
                        Log.d("MainActivity", "No documents found");
                        // You might want to update UI or perform other actions here
                    } else {
                        for (DocumentSnapshot document : queryDocumentSnapshots) { // The imported documents go into result
                            String teenagerId = document.getString("teenagerId");
                            String teenagerGender = document.getString("teenagerGender");
                            String teenagerName = document.getString("teenagerName");
                            String address = document.getString("address");
                            ListLayout3 item = new ListLayout3(teenagerId, teenagerGender, teenagerName, address);
                            itemList.add(item);
                        }
                        adapter.notifyDataSetChanged(); // Update recycler view
                    }
                })
                .addOnFailureListener(e -> {
                    // In case of failure
                    Log.w("MainActivity", "Error getting documents: ", e);
                });

        manager_nest_result_recyclerview.setAdapter(adapter);
//장채원 작성 end

        Button nestResultMatchButton = findViewById(R.id.nestResultMatchButton);

        nestResultMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerNestMatchResultActivity.this, ManagerMainActivity.class);
                startActivity(intent);
            }
        });

    }




}