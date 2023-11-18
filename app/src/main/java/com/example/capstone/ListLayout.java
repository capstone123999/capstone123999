package com.example.capstone;

public class ListLayout {
    private String lenderNotifyText;
    private String lenderNotifyTitle;

    public ListLayout(String lenderNotifyText, String lenderNotifyTitle) {
        this.lenderNotifyText = lenderNotifyText;
        this.lenderNotifyTitle = lenderNotifyTitle;
    }

    public String getLenderNotifyText() {
        return lenderNotifyText;
    }

    public String getLenderNotifyTitle() {
        return lenderNotifyTitle;
    }

    // If needed, you can also add setters for the fields
    public void setLenderNotifyText(String lenderNotifyText) {
        this.lenderNotifyText = lenderNotifyText;
    }

    public void setLenderNotifyTitle(String lenderNotifyTitle) {
        this.lenderNotifyTitle = lenderNotifyTitle;
    }
}
