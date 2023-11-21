package com.example.capstone;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MemberInfo {

    public void ShowMemberInfo(String userId) {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Query for the document reference of the user to delete
    db.collection("userInformation")
            .whereEqualTo("id", userId)
            .get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                    // Get the document reference and delete it
                    DocumentReference userDocRef = document.getReference();
                    userDocRef.delete()
                            .addOnSuccessListener(aVoid -> {
                                Log.d("id", "삭제 성공");
                                // Handle success
                            })
                            .addOnFailureListener(e -> {
                                Log.e("id", "삭제 실패", e);
                                // Handle failure
                            });
                }
            })
            .addOnFailureListener(e -> {
                Log.e("id", "검색 실패", e);
                // Handle failure in query
            });
}
}
