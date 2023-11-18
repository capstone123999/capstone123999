package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.example.capstone.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ManagerNotifyLender extends AppCompatActivity {
    private FirebaseFirestore db; // Firestore instance declaration
    private List<ListLayout> itemList; // array of list items
    private ListAdapter adapter; // Recycler View Adapter
    RecyclerView notify_info_lender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_notify_lender);

        notify_info_lender = findViewById(R.id.notify_info_lender);
        //ArrayAdapter<ListLayout> lenderNotifyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);

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
                        ListLayout item = new ListLayout(LenderNotifyText, LenderNotifyTitle);
                        itemList.add(item);
                    }
                    adapter.notifyDataSetChanged(); // Update recycler view
                })
                .addOnFailureListener(e -> {
                    // In case of failure
                    Log.w("MainActivity", "Error getting documents: ", e);
                });




        /*DocumentReference docRef = db.collection("LenderNotify").document("JZ0my653wgSnFqeVZueQ");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        lenderNotifyText = document.getString("LenderNotifyText");
                        lenderNotifyTitle = document.getString("LenderNotifyTitle");

                        ArrayList<CustomListView.ListData> listViewData = new ArrayList<>();

                        CustomListView.ListData listData = new CustomListView.ListData();

                        listData.mainImage = R.drawable.idicon;

                        listData.title = lenderNotifyTitle;
                        listData.body_1 = lenderNotifyText;

                        listViewData.add(listData);

                        ListAdapter oAdapter = new CustomListView(listViewData);
                        notify_info_lender.setAdapter(oAdapter);

                        notify_info_lender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        });*/
    }
}