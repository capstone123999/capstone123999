package com.example.capstone;

import com.google.firebase.firestore.DocumentSnapshot;

public class ListLayout {
    private String lenderNotifyText, lenderNotifyTitle;
    private DocumentSnapshot documentSnapshot;


    //lenderNotify
    public ListLayout(String lenderNotifyText, String lenderNotifyTitle, DocumentSnapshot documentSnapshot) {
        this.lenderNotifyText = lenderNotifyText;
        this.lenderNotifyTitle = lenderNotifyTitle;
        this.documentSnapshot = documentSnapshot;
    }

    public String getLenderNotifyText() {
        return lenderNotifyText;
    }

    public String getLenderNotifyTitle() {
        return lenderNotifyTitle;
    }

    public DocumentSnapshot getDocumentSnapshotListLayout() {
        return documentSnapshot;
    }


}
