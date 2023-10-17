package com.example.capstone;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/***
 * '보금자리 등록' 기능.
 * 1.신청받을 보금자리의 정보 입력 받음
 * 2.입력받은 정보 DB에 저장
 */

public class SubmitNest extends AppCompatActivity {

    EditText addressEditText;
    Button saveButton;
    // Define a private field for the ActivityResultLauncher
    ActivityResultLauncher<Intent> addressActivityResultLauncher;
    EditText submitNestAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest_submit);

        // 버튼을 누르면 AddressActivity를 띄워주는 부분
        Button submitNestAddressSearch = findViewById(R.id.submitNestAddressSearch);
        addressActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == AddressActivity.ADDRESS_REQUEST_CODE) {
                        Intent data = result.getData();
                        if (data != null) {
                            // Handle the result from AddressActivity here
                            String address = data.getStringExtra("address");
                            // Process the address as needed
                        }
                    }
                }
        );
        submitNestAddressSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitNest.this, AddressActivity.class);
                addressActivityResultLauncher.launch(intent);
            }
        });


/*
        // XML 레이아웃에서 필요한 뷰 요소들을 찾습니다.
        addressEditText = findViewById(R.id.submitNestAddressMore);
        saveButton = findViewById(R.id.submitNestButton);

        // 저장 버튼 클릭 이벤트 처리
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // EditText에서 주소 값을 가져옵니다.
                String managerAddress = addressEditText.getText().toString();

                // AsyncTask를 사용하여 주소를 저장합니다.
                new SaveAddressTask().execute(managerAddress);
            }
        });*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AddressActivity.ADDRESS_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // 주소를 가져와서 보여주는 부분
                String addressData = data != null ? data.getStringExtra("address") : null;
                submitNestAddress.setText(addressData);
            }
        }
    }
/*
    private class SaveAddressTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            final String managerAddress = params[0];
            final String[] result = new String[1];

            // 서버 URL
            String serverUrl = "http://192.168.0.15:8090/helpMe/save_address.php";//로컬 주소. 실제 완성시에는 바꾸기

            // Volley의 StringRequest를 사용하여 POST 요청을 보냅니다.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, serverUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // 서버 응답을 받아서 result 배열에 저장
                            result[0] = response;
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // 에러 발생 시 에러 메시지를 result 배열에 저장
                            result[0] = /*"서버 응답 오류: " +*/ /*error.getMessage();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    // POST 요청에 보낼 파라미터 설정
                    Map<String, String> params = new HashMap<>();
                    params.put("address", managerAddress);
                    return params;
                }
            };

            // Volley 요청 큐에 요청을 추가하고 실행
            RequestQueue requestQueue = Volley.newRequestQueue(SubmitNest.this);
            requestQueue.add(stringRequest);

            // 결과를 반환할 때까지 대기
            while (result[0] == null) {
                try {
                    Thread.sleep(100); // 100 밀리초 동안 대기
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return result[0];
        }

        @Override
        protected void onPostExecute(String result) {
            // 저장 결과를 처리하거나 사용자에게 메시지를 표시합니다.
            Toast.makeText(SubmitNest.this, result, Toast.LENGTH_SHORT).show();
        }
    }
    */
}