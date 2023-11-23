package com.example.capstone;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/***
 * '보금자리 등록' 기능.
 * 1.신청받을 보금자리의 정보 입력 받음
 * 2.입력받은 정보 DB에 저장
 *
 * 다음에 할 것 : 체크박스를 체크해도 값이 1이 안되는 현상 수정.
 */

public class SubmitNest extends AppCompatActivity{

    // 초기변수설정
    CheckBox checkbox1, checkbox2, checkbox3, checkbox4;
    int roomSizeResult;
    String spinnerChoice1, spinnerChoice2, spinnerChoice3, submitNestAddressMoreResult, submitNestAddressSearchResult, checkboxResult1,checkboxResult2,checkboxResult3, checkboxResult4;
    EditText edit_addr, submitNestAddressMore, roomSize;
    Button submitNestAddressSearch;
    ArrayAdapter<String> adapter1, adapter2, adapter3= null;
    Spinner spinner1, spinner2, spinner3 = null;
    // 주소 요청코드 상수 requestCode
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    private FirebaseFirestore db;
    String[] items = {"개월 수를 선택해주세요", "1개월", "2개월", "3개월", "4개월", "5개월", "6개월", "7개월", "8개월", "9개월", "10개월", "11개월", "12개월"};
    String[] items2 = {"시간을 선택해주세요", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest_submit);


        // UI 요소 연결
        edit_addr = findViewById(R.id.submitNestAddress);
        submitNestAddressSearch = findViewById(R.id.submitNestAddressSearch);
        submitNestAddressMore = findViewById(R.id.submitNestAddressMore);
        roomSize = findViewById(R.id.roomSize);
        checkbox1 = findViewById(R.id.serviceCheckbox1);
        checkbox2 = findViewById(R.id.serviceCheckbox2);
        checkbox3 = findViewById(R.id.serviceCheckbox3);
        checkbox4 = findViewById(R.id.serviceCheckbox4);

        db = FirebaseFirestore.getInstance();

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
                if (status == NetworkStatus.TYPE_MOBILE || status == NetworkStatus.TYPE_WIFI) {

                    Log.i("주소설정페이지", "주소입력창 클릭");
                    Intent i = new Intent(getApplicationContext(), AddressActivity.class);
                    // 화면전환 애니메이션 없애기
                    overridePendingTransition(0, 0);
                    // 주소결과
                    startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);

                } else {
                    Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //submitNestButton 클릭시 이벤트 지정
        Button submitNestButton = findViewById(R.id.submitNestButton);
        submitNestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //실평수 부분
                try {
                    if(roomSize.length()==0){
                        Toast.makeText(getApplicationContext(),"빈 칸 없이 입력해주세요",Toast.LENGTH_LONG).show();
                        return;
                    }
                    roomSizeResult = Integer.parseInt(roomSize.getText().toString());

                }catch (NumberFormatException e){
                    Toast.makeText(SubmitNest.this, "실평수를 공백 없이 입력해주세요", Toast.LENGTH_SHORT).show();
                }

                //상세 주소 부분
                try {
                    submitNestAddressMoreResult = submitNestAddressMore.getText().toString();
                    if(submitNestAddressMoreResult.length()==0){
                        Toast.makeText(getApplicationContext(),"빈 칸 없이 입력해주세요",Toast.LENGTH_LONG).show();
                        return;
                    }
                }catch (NumberFormatException e){
                    Toast.makeText(SubmitNest.this, "상세 주소를 써주세요", Toast.LENGTH_SHORT).show();
                }
                //정보 db에 저장
                saveSubmitNest();
                //성공적 저장 토스트 출력 후 LenderMain 화면으로 연결
                Toast.makeText(SubmitNest.this, "보금자리 정보가 저장되었습니다", Toast.LENGTH_SHORT).show();
                Intent fromSubmitNestToLenderMain = new Intent(SubmitNest.this, LenderMainActivity.class);
                startActivity(fromSubmitNestToLenderMain);
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
                        submitNestAddressSearchResult = data;
                    }
                }
                break;
        }
    }
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.serviceCheckbox1:
                if (checked) {
                    checkboxResult1 = checkbox1.getText().toString();
                }
                else{
                    return;
                }
                break;
            case R.id.serviceCheckbox2:
                if (checked){
                    checkboxResult2 = checkbox2.getText().toString();
                }
                else{
                    return;
                }
                break;
            case R.id.serviceCheckbox3:
                if (checked){
                    checkboxResult3 = checkbox3.getText().toString();
                }
                else{
                    return;
                }
                break;
            case R.id.serviceCheckbox4:
                if (checked){
                    checkboxResult4 = checkbox4.getText().toString();
                }
                else{
                    return;
                }
                break;
        }
    }

    public void saveSubmitNest() {

        String submitNestUserId = getSharedPreferences("userPrefs", MODE_PRIVATE).getString("id", null);

        if (submitNestUserId != null && !submitNestUserId.isEmpty()) {
            db.collection("userInformation")
                    .whereEqualTo("id", submitNestUserId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // Firestore에서 필드 값을 가져와서 변수에 저장
                                    String name = document.getString("name");
                                    String gender = document.getString("gender");
                                    String id = document.getString("id");
                                    String usertype= document.getString("usertype");

                                    // Write a message to the database
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    Map<String, Object> submitNest = new HashMap<>();
                                    submitNest.put("lenderId", id);
                                    submitNest.put("lenderGender", gender);
                                    submitNest.put("lenderName", name);
                                    submitNest.put("usertype", usertype);
                                    submitNest.put("address", submitNestAddressSearchResult);
                                    submitNest.put("addressMore", submitNestAddressMoreResult);
                                    submitNest.put("rental period", spinnerChoice1);
                                    submitNest.put("roomSize", roomSizeResult);
                                    submitNest.put("serviceCheckbox1",checkboxResult1);
                                    submitNest.put("serviceCheckbox2",checkboxResult2);
                                    submitNest.put("serviceCheckbox3",checkboxResult3);
                                    submitNest.put("serviceCheckbox4",checkboxResult4);
                                    submitNest.put("callTime1",spinnerChoice2);
                                    submitNest.put("callTime2",spinnerChoice3);

                                    // Add a new document with a generated ID
                                    db.collection("submitNest")
                                            .add(submitNest)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error adding document", e);
                                                }
                                            });



                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        } else {
            Log.d(TAG, "SharedPreferences 'id' is null or empty");
            // Handle this case as needed
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