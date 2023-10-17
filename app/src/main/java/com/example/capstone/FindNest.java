package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/***
 * '보금자리 찾기' 기능.
 * 1.회원가입에서 받은 개인정보를 중 필요한 정보를 갖고와서 매칭에 사용한다.
 * 2.매칭 완료되면 '매칭 완료(find_nest_result)'화면을 보여준 뒤 <확인> 버튼을 누르면 ①경찰에게 알림이 가고 ②청소년에겐 가까운 파출소 위치를 안내해준다.
 */
public class FindNest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest_find);

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