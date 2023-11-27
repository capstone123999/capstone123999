package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManagerNestSubmitActivity extends AppCompatActivity implements ListAdapter2.OnItemClickListener {

    private FirebaseFirestore db; // Firestore instance declaration
    private List<ListLayout2> itemList; // array of list items
    private ListAdapter2 adapter; // Recycler View Adapter
    RecyclerView manager_nest_submit_recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_nest_submit);

        //민연수 작성 시작
        Button submitNestManagerButton = findViewById(R.id.submitNestManagerButton);
        submitNestManagerButton.setOnClickListener(v -> {
            Intent intent = new Intent(ManagerNestSubmitActivity.this, ManagerMainActivity.class);
            startActivity(intent);
        });
        //민연수 작성 끝

        //장채원 작성 시작
        manager_nest_submit_recyclerview = findViewById(R.id.manager_nest_submit_recyclerview);
        //DB 정보 가져오기
        db = FirebaseFirestore.getInstance();

        itemList = new ArrayList<>();
        adapter = new ListAdapter2((ArrayList<ListLayout2>) itemList);

        if (manager_nest_submit_recyclerview != null) {
            // Set LayoutManager and Adapter for the RecyclerView
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            manager_nest_submit_recyclerview.setLayoutManager(layoutManager);
            manager_nest_submit_recyclerview.setAdapter(adapter);
        } else {
            // Handle the case if notify_info_lender is not found or not a RecyclerView
            Log.e("RecyclerView Error", "manager_nest_submit_recyclerview not found or not a RecyclerView");
        }


        db.collection("submitNest") // Collection to work with
                .get() // Get document
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    // if successful
                    itemList.clear();
                    for (DocumentSnapshot document : queryDocumentSnapshots) { // The imported documents go into result
                        String manager_nest_submit_address = document.getString("address");
                        String manager_nest_submit_address_more = document.getString("addressMore");
                        String manager_nest_submit_callTime1 = document.getString("callTime1");
                        String manager_nest_submit_callTime2 = document.getString("callTime2");
                        String manager_nest_submit_lenderGender = document.getString("lenderGender");
                        String manager_nest_submit_lenderId = document.getString("lenderId");
                        String manager_nest_submit_lenderName = document.getString("lenderName");
                        String manager_nest_submit_rental_period = document.getString("rental period");
                        Long roomSizeLong = document.getLong("roomSize");
                        int manager_nest_submit_rental_roomSize = roomSizeLong != null ? roomSizeLong.intValue() : 0;
                        String manager_nest_submit_rental_serviceCheckbox1 = document.getString("serviceCheckbox1");
                        String manager_nest_submit_rental_serviceCheckbox2 = document.getString("serviceCheckbox2");
                        String manager_nest_submit_rental_serviceCheckbox3 = document.getString("serviceCheckbox3");
                        String manager_nest_submit_rental_serviceCheckbox4 = document.getString("serviceCheckbox4");
                        String manager_nest_submit_rental_usertype = document.getString("usertype");
                        ListLayout2 item = new ListLayout2(manager_nest_submit_address, manager_nest_submit_address_more,
                                manager_nest_submit_callTime1, manager_nest_submit_callTime2, manager_nest_submit_lenderGender,
                                manager_nest_submit_lenderId, manager_nest_submit_lenderName, manager_nest_submit_rental_period,
                                manager_nest_submit_rental_roomSize, manager_nest_submit_rental_serviceCheckbox1,
                                manager_nest_submit_rental_serviceCheckbox2, manager_nest_submit_rental_serviceCheckbox3,
                                manager_nest_submit_rental_serviceCheckbox4, manager_nest_submit_rental_usertype,document);
                        itemList.add(item);
                    }
                    adapter.notifyDataSetChanged(); // Update recycler view
                })
                .addOnFailureListener(e -> {
                    // In case of failure
                    Log.w("MainActivity", "Error getting documents: ", e);
                });

        adapter.setOnItemClickListener(this);
        manager_nest_submit_recyclerview.setAdapter(adapter);


    }


    @Override
    public void onItemClick(ListLayout2 item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this); // 'this' refers to your Activity or Fragment context

        // Set dialog title and message using data from 'item'
        builder.setTitle("승인하시겠습니까")
                .setMessage("승인하려면 버튼을 눌러주십시오")
                .setPositiveButton("승인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemList.remove(item); // Remove the selected item from the list
                        adapter.notifyDataSetChanged(); // Notify the adapter of the data change
                        Toast.makeText(ManagerNestSubmitActivity.this, "승인되었습니다", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }).setNegativeButton("미승인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DocumentSnapshot documentSnapshot = item.getDocumentSnapshot(); // Get the associated DocumentSnapshot
                        if (documentSnapshot != null) {
                            String itemId = documentSnapshot.getId(); // Get the ID from DocumentSnapshot
                            removeItemFromFirestore(itemId); // Call the method to remove item from Firestore using ID
                            itemList.remove(item); // Remove the selected item from the list
                            adapter.notifyDataSetChanged(); // Notify the adapter of the data change
                            Toast.makeText(ManagerNestSubmitActivity.this, "미승인되었습니다", Toast.LENGTH_SHORT).show();
                            dialog.dismiss(); // Dismiss the dialog
                        } else {
                            // Handle the case where DocumentSnapshot is null
                        }
                    }
                });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void removeItemFromFirestore(String itemId) {
        // Reference to your Firestore collection where items are stored
        CollectionReference itemsCollection = FirebaseFirestore.getInstance().collection("submitNest");

        // Delete the item from Firestore based on its unique ID
        itemsCollection.document(itemId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firestore", "DocumentSnapshot successfully deleted!");
                        // Item successfully deleted from Firestore
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error deleting document", e);
                        // Failed to delete item from Firestore
                    }
                });
    }
}