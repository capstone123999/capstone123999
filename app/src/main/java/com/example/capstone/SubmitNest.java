package com.example.capstone;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class SubmitNest extends AppCompatActivity{

    // 초기변수설정
    String spinnerChoice1, spinnerChoice2, spinnerChoice3;
    String log = "log";
    EditText edit_addr;
    Button submitNestAddressSearch;
    ArrayAdapter<String> adapter1, adapter2, adapter3= null;
    Spinner spinner1, spinner2, spinner3 = null;
    // 주소 요청코드 상수 requestCode
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    String[] items = {"개월 수를 선택해주세요", "1개월", "2개월", "3개월", "4개월", "5개월", "6개월", "7개월", "8개월", "9개월", "10개월", "11개월", "12개월"};
    String[] items2 = {"시간을 선택해주세요", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest_submit);


        //submitNestButton 클릭시 이벤트 지정
        Button submitNestButton = findViewById(R.id.submitNestButton);
        submitNestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SubmitNest.this, spinnerChoice1 + "," + spinnerChoice2 + ","+spinnerChoice3, Toast.LENGTH_LONG).show();
            }
        });

        // UI 요소 연결
        edit_addr = findViewById(R.id.submitNestAddress);
        submitNestAddressSearch = findViewById(R.id.submitNestAddressSearch);
        //개월 수 선택 스피너
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner1=findViewById(R.id.rentPeriod);
        spinner1.setAdapter(adapter1);
        spinner1.setSelection(0);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                Intent monthIntent = new Intent();
                spinnerChoice1=adapter1.getItem(i);
                monthIntent.putExtra("hourIntent1", spinnerChoice1);
                /*switch (position) {
                    case 0:
                        Toast.makeText(SubmitNest.this, "항목을 선택해주세요", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        monthIntent.putExtra("monthIntentData", items[1]);
                        Log.d(log, "monthIntent값 : ");
                        break;
                    case 2:
                        monthIntent.putExtra("monthIntentData", items[2]);
                        break;
                    case 3:
                        monthIntent.putExtra("monthIntentData", items[3]);
                        break;
                    case 4:
                        monthIntent.putExtra("monthIntentData", items[4]);
                        break;
                    case 5:
                        monthIntent.putExtra("monthIntentData", items[5]);
                        break;
                    case 6:
                        monthIntent.putExtra("monthIntentData", items[6]);
                        break;
                    case 7:
                        monthIntent.putExtra("monthIntentData", items[7]);
                        break;
                    case 8:
                        monthIntent.putExtra("monthIntentData", items[8]);
                        break;
                    case 9:
                        monthIntent.putExtra("monthIntentData", items[9]);
                        break;
                    case 10:
                        monthIntent.putExtra("monthIntentData", items[10]);
                        break;
                    case 11:
                        monthIntent.putExtra("monthIntentData", items[11]);
                        break;
                    case 12:
                        monthIntent.putExtra("monthIntentData", items[12]);
                        break;
                    default:
                        Toast.makeText(SubmitNest.this, "항목을 선택해주세요", Toast.LENGTH_SHORT).show();
                        break;
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //시간대 선택 스피너 (좌)
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        spinner2=findViewById(R.id.callTime1);
        spinner2.setAdapter(adapter2);
        spinner2.setSelection(0);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                Intent hourIntent1 = new Intent();
                spinnerChoice2=adapter2.getItem(i);
                hourIntent1.putExtra("hourIntent1", spinnerChoice2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //시간대 선택 스피너 (우)
        adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        spinner3=findViewById(R.id.callTime2);
        spinner3.setAdapter(adapter3);
        spinner3.setSelection(0);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                Intent hourIntent1 = new Intent();
                spinnerChoice3=adapter3.getItem(i);
                hourIntent1.putExtra("hourIntent1", spinnerChoice3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // 터치 안되게 막기
        edit_addr.setFocusable(false);

        // 주소입력 클릭
        submitNestAddressSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("주소설정페이지", "주소입력창 클릭");
                int status = NetworkStatus.getConnectivityStatus(getApplicationContext());
                if(status == NetworkStatus.TYPE_MOBILE || status == NetworkStatus.TYPE_WIFI) {

                    Log.i("주소설정페이지", "주소입력창 클릭");
                    Intent i = new Intent(getApplicationContext(), AddressActivity.class);
                    // 화면전환 애니메이션 없애기
                    overridePendingTransition(0, 0);
                    // 주소결과
                    startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);

                }else {
                    Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.i("test", "onActivityResult");

        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        Log.i("test", "data:" + data);
                        edit_addr.setText(data);
                    }
                }
                break;
        }
    }
}

/*


    EditText addressEditText;
    Button saveButton;
    // Define a private field for the ActivityResultLauncher
    EditText submitNestAddress;
    String Tag = "a";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest_submit);

        // 버튼을 누르면 AddressActivity를 띄워주는 부분
        Button submitNestAddressSearch = findViewById(R.id.submitNestAddressSearch);

        submitNestAddressSearch.setOnClickListener(v -> {
            Intent intent = new Intent(SubmitNest.this, AddressActivity.class);
            addressActivityResultLauncher.launch(intent);
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
        });
    }*/

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

    private final ActivityResultLauncher<Intent> addressActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode()==RESULT_OK){
                    if(result.getData()!=null){
                        String data = result.getData().getStringExtra("data");
                        submitNestAddress.setText(data);
                    }
                    Log.d(Tag, "데이터 null임");
                }
                Log.d(Tag, "데이터 안받아짐");
            }
    );

}
        */