package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ManagerNotifyTeenager extends AppCompatActivity {

    private FirebaseFirestore db; // Firestore instance declaration
    private List<ListLayout> itemList; // array of list items
    private ListAdapter adapter; // Recycler View Adapter
    RecyclerView notify_info_teenager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_notify_teenager);

        notify_info_teenager = findViewById(R.id.notify_info_teenager);

        //DB 정보 가져오기
        db = FirebaseFirestore.getInstance();

        itemList = new ArrayList<>();
        adapter = new ListAdapter((ArrayList<ListLayout>) itemList);

        if (notify_info_teenager != null) {
            // Set LayoutManager and Adapter for the RecyclerView
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            notify_info_teenager.setLayoutManager(layoutManager);
            notify_info_teenager.setAdapter(adapter);
        } else {
            // Handle the case if notify_info_lender is not found or not a RecyclerView
            Log.e("RecyclerView Error", "notify_info_lender not found or not a RecyclerView");
        }


        db.collection("teenagerNotify") // Collection to work with
                .get() // Get document
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    // if successful
                    itemList.clear();
                    for (DocumentSnapshot document : queryDocumentSnapshots) { // The imported documents go into result
                        String teenagerNotifyText = document.getString("TeenagerNotifyText");
                        String teenagerNotifyTitle = document.getString("TeenagerNotifyTitle");
                        ListLayout item = new ListLayout(teenagerNotifyText, teenagerNotifyTitle);
                        itemList.add(item);
                    }
                    adapter.notifyDataSetChanged(); // Update recycler view
                })
                .addOnFailureListener(e -> {
                    // In case of failure
                    Log.w("MainActivity", "Error getting documents: ", e);
                });


    }
}