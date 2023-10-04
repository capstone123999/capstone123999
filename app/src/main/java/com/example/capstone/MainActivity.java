package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 주소 검색 요청 예시 (서울시 청파로 464)
        String query = "서울시 청파로 464";
        searchAddress(query);

        Button teenagerMainEmergency = findViewById(R.id.teenagerMainEmergency);
        teenagerMainEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Emergency.class);
                startActivity(intent);
            }
        });

        Button teenagerMainNotify = findViewById(R.id.teenagerMainNotify);
        teenagerMainNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Notify.class);
                startActivity(intent);
            }
        });

        Button teenagerMainNestFind = findViewById(R.id.teenagerMainNestFind);
        teenagerMainNestFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FindNest.class);
                startActivity(intent);
            }
        });

        ImageButton teenagerMainMyPageImage = findViewById(R.id.teenagerMainMyPageImage);
        teenagerMainMyPageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyPage.class);
                startActivity(intent);
            }
        });
    }

    private void searchAddress(String query) {
        new Thread(() -> {
            try {
                OkHttpClient client =
                        new OkHttpClient();

                // API 요청 URL 생성
                String url =
                        "https://dapi.kakao.com/v2/local/search/address.json?query=" + query;

                Request request =
                        new Request.Builder()
                                .url(url)
                                .addHeader("Authorization", "KakaoAK " + KAKAO_API_KEY)
                                .build();

                Response response =
                        client.newCall(request).execute();

                if (response.isSuccessful()) {
                    Gson gson =
                            new Gson();

                    JsonObject jsonObject =
                            gson.fromJson(response.body().string(), JsonObject.class);

                    Log.d(TAG, jsonObject.toString());

                    // 결과 처리 및 원하는 정보 추출 가능

                } else {

                    Log.e(TAG, "API request failed");

                }

            } catch (Exception e) {

                e.printStackTrace();

            }

        }).start();
    }
}