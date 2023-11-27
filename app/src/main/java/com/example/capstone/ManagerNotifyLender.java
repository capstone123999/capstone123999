package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.capstone.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ManagerNotifyLender extends AppCompatActivity implements ListAdapter.OnItemClickListener {
    private FirebaseFirestore db; // Firestore instance declaration
    private List<ListLayout> itemList; // array of list items
    private ListAdapter adapter; // Recycler View Adapter
    RecyclerView notify_info_lender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_notify_lender);

        notify_info_lender = findViewById(R.id.notify_info_lender);

        //DB 정보 가져오기
        db = FirebaseFirestore.getInstance();

        itemList = new ArrayList<>();
        adapter = new ListAdapter((ArrayList<ListLayout>) itemList);

        if (notify_info_lender != null) {
            // Set LayoutManager and Adapter for the RecyclerView
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            notify_info_lender.setLayoutManager(layoutManager);
            notify_info_lender.setAdapter(adapter);
        } else {
            // Handle the case if notify_info_lender is not found or not a RecyclerView
            Log.e("RecyclerView Error", "notify_info_lender not found or not a RecyclerView");
        }


        db.collection("LenderNotify") // Collection to work with
                .get() // Get document
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    // if successful
                    itemList.clear();
                    for (DocumentSnapshot document : queryDocumentSnapshots) { // The imported documents go into result
                        String LenderNotifyText = document.getString("LenderNotifyText");
                        String LenderNotifyTitle = document.getString("LenderNotifyTitle");
                        ListLayout item = new ListLayout(LenderNotifyText, LenderNotifyTitle, document);
                        itemList.add(item);
                    }
                    adapter.notifyDataSetChanged(); // Update recycler view
                })
                .addOnFailureListener(e -> {
                    // In case of failure
                    Log.w("MainActivity", "Error getting documents: ", e);
                });

        adapter.setOnItemClickListener(this);
        notify_info_lender.setAdapter(adapter);
    }
    public void onItemClick(ListLayout item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ManagerNotifyLender.this);

        // Set dialog title and message using data from 'item'
        builder.setTitle("승인하시겠습니까")
                .setMessage("승인하려면 버튼을 눌러주십시오")
                .setPositiveButton("승인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemList.remove(item); // Remove the selected item from the list
                        adapter.notifyDataSetChanged(); // Notify the adapter of the data change
                        Toast.makeText(ManagerNotifyLender.this, "승인되었습니다", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }).setNegativeButton("미승인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DocumentSnapshot documentSnapshot = item.getDocumentSnapshotListLayout(); // Get the associated DocumentSnapshot
                        if (documentSnapshot != null) {
                            String itemId = documentSnapshot.getId(); // Get the ID from DocumentSnapshot
                            removeItemFromFirestore(itemId); // Call the method to remove item from Firestore using ID
                            itemList.remove(item); // Remove the selected item from the list
                            adapter.notifyDataSetChanged(); // Notify the adapter of the data change
                            Toast.makeText(ManagerNotifyLender.this, "미승인되었습니다", Toast.LENGTH_SHORT).show();
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
        CollectionReference itemsCollection = FirebaseFirestore.getInstance().collection("LenderNotify");

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
