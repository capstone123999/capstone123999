package com.example.capstone;

public class ListLayout3 {

    private String teenagerId, teenagerGender, teenagerName, address;

    public ListLayout3(String teenagerId, String teenagerGender, String teenagerName, String address) {
        this.teenagerId = teenagerId;
        this.teenagerGender = teenagerGender;
        this.teenagerName = teenagerName;
        this.address = address;
    }

    public String getMatchResultText() {
        return teenagerName;
    } //getLenderNotifyText

    public String getMatchResultTitle() {return "청소년 성별 : " + teenagerGender +"\n"+ "청소년 ID : " + teenagerId +"\n"+ "보금자리 주소 : "+address;} //getLenderNotifyTitle
}

