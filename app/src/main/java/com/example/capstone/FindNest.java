package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/***
 * '보금자리 찾기' 기능.
 * 1.회원가입에서 받은 개인정보를 중 필요한 정보를 갖고와서 매칭에 사용한다.
 * 2.매칭 완료되면 '매칭 완료(find_nest_result)'화면을 보여준 뒤 <확인> 버튼을 누르면 ①경찰에게 알림이 가고 ②청소년에겐 가까운 파출소 위
 * 치를 안내해준다.
 */
public class FindNest extends AppCompatActivity {

    Spinner spinner1;
    String[] items1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest_find);

        // 스피너 아이템 설정
        items1 = new String[]{"개월 수를 선택해주세요", "1개월", "2개월", "3개월", "4개월", "5개월", "6개월", "7개월", "8개월", "9개월", "10개월", "11개월", "12개월"};

        // 스피너 초기화 및 어댑터 설정
        spinner1 = findViewById(R.id.monthChoice);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);

        spinner1.setAdapter(adapter1);

        // 스피너 아이템 선택 이벤트 처리
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = items1[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택되지 않았을 때의 처리
            }
        });

        Button nestFindRegisterButton = findViewById(R.id.nestFindRegisterButton);
        nestFindRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // activity_google_map으로 이동하는 인텐트 생성
                Intent intent = new Intent(FindNest.this, Map.class);
                startActivity(intent);
            }
        });
    }
}