package com.example.capstone;

import com.google.firebase.firestore.DocumentSnapshot;

public class ListLayout2 {
    private String manager_nest_submit_address,manager_nest_submit_address_more, manager_nest_submit_callTime1, manager_nest_submit_callTime2,
            manager_nest_submit_lenderGender, manager_nest_submit_lenderId, manager_nest_submit_lenderName,manager_nest_submit_rental_period,
            manager_nest_submit_serviceCheckbox1, manager_nest_submit_serviceCheckbox2, manager_nest_submit_serviceCheckbox3,
            manager_nest_submit_serviceCheckbox4, manager_nest_submit_usertype;
    private int manager_nest_submit_roomSize;
    private DocumentSnapshot documentSnapshot;

    //ManagerNestSubmitAcitity
    public ListLayout2(String manager_nest_submit_address, String manager_nest_submit_address_more, String manager_nest_submit_callTime1,
                       String manager_nest_submit_callTime2, String manager_nest_submit_lenderGender, String manager_nest_submit_lenderId,
                       String manager_nest_submit_lenderName, String manager_nest_submit_rental_period, int manager_nest_submit_roomSize,
                       String manager_nest_submit_serviceCheckbox1, String manager_nest_submit_serviceCheckbox2,
                       String manager_nest_submit_serviceCheckbox3, String manager_nest_submit_serviceCheckbox4, String manager_nest_submit_usertype,
                       DocumentSnapshot documentSnapshot)
    {
        this.manager_nest_submit_address = manager_nest_submit_address;
        this.manager_nest_submit_address_more = manager_nest_submit_address_more;
        this.manager_nest_submit_callTime1 = manager_nest_submit_callTime1;
        this.manager_nest_submit_callTime2 = manager_nest_submit_callTime2;
        this.manager_nest_submit_lenderGender = manager_nest_submit_lenderGender;
        this.manager_nest_submit_lenderId = manager_nest_submit_lenderId;
        this.manager_nest_submit_lenderName = manager_nest_submit_lenderName;
        this.manager_nest_submit_rental_period = manager_nest_submit_rental_period;
        this.manager_nest_submit_roomSize = manager_nest_submit_roomSize;
        this.manager_nest_submit_serviceCheckbox1 = manager_nest_submit_serviceCheckbox1;
        this.manager_nest_submit_serviceCheckbox2 = manager_nest_submit_serviceCheckbox2;
        this.manager_nest_submit_serviceCheckbox3 = manager_nest_submit_serviceCheckbox3;
        this.manager_nest_submit_serviceCheckbox4 = manager_nest_submit_serviceCheckbox4;
        this.manager_nest_submit_usertype = manager_nest_submit_usertype;
        this.documentSnapshot = documentSnapshot;
    }

    public String getNestInfoTitle() {
        return manager_nest_submit_address;
    }

    public String getNestInfo() {
        // You can concatenate or format the values you want to display as a single string
        return "상세주소 : " + manager_nest_submit_address_more + "\n" +"전화 가능 시작 시작 : " +  manager_nest_submit_callTime1  + "\n" + "전화 가능 종료 시간 : " + manager_nest_submit_callTime2 + "\n"
                + "대여자 성별 : " + manager_nest_submit_lenderGender + "\n"+ "대여자 ID : " + manager_nest_submit_lenderId + "\n"+ "대여자 이름 : " + manager_nest_submit_lenderName + "\n"
                + "대여 기간 : " + manager_nest_submit_rental_period + "\n"+"방 평수 : " + manager_nest_submit_roomSize + "\n"+ "가능한 서비스 : " + manager_nest_submit_serviceCheckbox1 + ", "
                + manager_nest_submit_serviceCheckbox2 + ",  " + manager_nest_submit_serviceCheckbox3 + ", " + manager_nest_submit_serviceCheckbox4 + "\n"
                + "유저 유형 : " + manager_nest_submit_usertype;
    }

    public DocumentSnapshot getDocumentSnapshot() {
        return documentSnapshot;
    }

}
