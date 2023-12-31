package com.example.capstone;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        ImageButton backwards = findViewById(R.id.backwards);
        backwards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, TeenagerMainActivity.class);
                startActivity(intent);
            }
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(this, "지도를 표시할 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;

        // 현재 위치 표시
        if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
        } else {
            googleMap.setMyLocationEnabled(true);

            // 현재 위치로 이동
            fusedLocationClient.getLastLocation().addOnSuccessListener(MapActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f));
                    }
                }
            });

            addMarkers();
        }
    }

    private void addMarkers() {
        addMarker(37.5138791, 127.0293066, "서울강남경찰서");
        addMarker(37.5152074, 127.0338473, "서울강남경찰서");
        addMarker(37.5148489, 127.0605684, "서울강남경찰서");
        addMarker(37.5006784, 127.0489432, "서울강남경찰서");
        addMarker(37.5210873, 127.0532270, "서울강남경찰서");
        addMarker(37.5183334, 127.0241702, "서울강남경찰서");
        addMarker(37.5293416, 127.0355918, "서울강남경찰서");

        addMarker(37.5253425, 127.1456295, "서울강동경찰서");
        addMarker(37.5536205, 127.1560818, "서울강동경찰서");
        addMarker(37.5648437, 127.1732748, "서울강동경찰서");
        addMarker(37.5573421, 127.1517305, "서울강동경찰서");
        addMarker(37.5561836, 127.1658284, "서울강동경찰서");

        addMarker(37.6306502, 127.0175895, "서울강북경찰서");
        addMarker(37.6409113, 127.0171016, "서울강북경찰서");
        addMarker(37.6443456, 127.0265710, "서울강북경찰서");
        addMarker(37.6394394, 127.0123991, "서울강북경찰서");
        addMarker(37.6359900, 127.0326008, "서울강북경찰서");
        addMarker(37.6236112, 127.0481447, "서울강북경찰서");
        addMarker(37.6248025, 127.0181839, "서울강북경찰서");

        addMarker(37.5798086, 126.8146637, "서울강서경찰서");
        addMarker(37.5447718, 126.8619202, "서울강서경찰서");
        addMarker(37.5446703, 126.8351964, "서울강서경찰서");

        addMarker(37.4746571, 126.9778055, "서울관악경찰서");
        addMarker(37.4611268, 126.9192473, "서울관악경찰서");
        addMarker(37.4802834, 126.9155529, "서울관악경찰서");

        addMarker(37.5637230, 127.0827863, "서울광진경찰서");
        addMarker(37.5602505, 127.0814843, "서울광진경찰서");
        addMarker(37.5688192, 127.0801404, "서울광진경찰서");
        addMarker(37.5653353, 127.0872180, "서울광진경찰서");
        addMarker(37.5338683, 127.0716102, "서울광진경찰서");
        addMarker(37.5366309, 127.0769210, "서울광진경찰서");
        addMarker(37.5351513, 127.0636287, "서울광진경찰서");
        addMarker(37.5458868, 127.0916497, "서울광진경찰서");

        addMarker(37.4866541, 126.8913691, "서울구로경찰서");
        addMarker(37.4791578, 126.8926325, "서울구로경찰서");
        addMarker(37.4834277, 126.8418100, "서울구로경찰서");

        addMarker(37.4542242, 126.8998381, "서울금천경찰서");
        addMarker(37.4799190, 126.8880499, "서울금천경찰서");
        addMarker(37.4646641, 126.8948276, "서울금천경찰서");

        addMarker(37.5680342, 126.9800551, "서울남대문경찰서");
        addMarker(37.5562987, 126.9718126, "서울남대문경찰서");
        addMarker(37.5635808, 126.9839660, "서울남대문경찰서");
        addMarker(37.5594216, 126.9762398, "서울남대문경찰서");
        addMarker(37.5578847, 126.9671311, "서울남대문경찰서");
        addMarker(37.5578126, 126.9790112, "서울남대문경찰서");
        addMarker(37.5638544, 126.9754490, "서울남대문경찰서");

        addMarker(37.6714827, 127.0778607, "서울노원경찰서");
        addMarker(37.6796671, 127.0550431, "서울노원경찰서");

        addMarker(37.6435218, 127.0345748, "서울도봉경찰서");
        addMarker(37.6644651, 127.0399956, "서울도봉경찰서");
        addMarker(37.6585014, 127.0271436, "서울도봉경찰서");
        addMarker(37.6794987, 127.0434863, "서울도봉경찰서");
        addMarker(37.6839868, 127.0492833, "서울도봉경찰서");
        addMarker(37.6575676, 127.0380293, "서울도봉경찰서");
        addMarker(37.6474873, 127.0255867, "서울도봉경찰서");

        addMarker(37.5840966, 127.0452633, "서울강서경찰서");
        addMarker(37.5855849, 127.0420335, "서울강서경찰서");
        addMarker(37.5761361, 127.0479386, "서울강서경찰서");
        addMarker(37.5863258, 127.0542318, "서울강서경찰서");
        addMarker(37.5694324, 127.0648990, "서울강서경찰서");
        addMarker(37.5767834, 127.0743654, "서울강서경찰서");
        addMarker(37.5846694, 127.0680102, "서울강서경찰서");
        addMarker(37.5910751, 127.0518528, "서울강서경찰서");

        addMarker(37.4985670, 126.9206022, "서울동작경찰서");

        addMarker(37.5782477, 126.8938555, "서울마포경찰서");
        addMarker(37.5543305, 126.9027303, "서울마포경찰서");
        addMarker(37.5603349, 126.9237597, "서울마포경찰서");

        addMarker(37.5015562, 126.9980944, "서울방배경찰서");
        addMarker(37.4941411, 126.9888281, "서울방배경찰서");
        addMarker(37.4839725, 126.9953204, "서울방배경찰서");

        addMarker(37.5952417, 126.9464180, "서울서대문경찰서");
        addMarker(37.5703278, 126.9329029, "서울서대문경찰서");
        addMarker(37.5760219, 126.9240678, "서울서대문경찰서");
        addMarker(37.5829851, 126.9128737, "서울서대문경찰서");
        addMarker(37.5881612, 126.9445399, "서울서대문경찰서");
        addMarker(37.5836756, 126.9361657, "서울서대문경찰서");

        addMarker(37.6081391, 126.9323304, "서울서부경찰서");
        addMarker(37.5905279, 126.9175364, "서울서부경찰서");
        addMarker(37.6044279, 126.9151883, "서울서부경찰서");

        addMarker(37.4875131, 127.0289593, "서울서초경찰서");
        addMarker(37.4919865, 127.0107859, "서울서초경찰서");
        addMarker(37.4845428, 127.0378441, "서울서초경찰서");
        addMarker(37.4852897, 127.0167473, "서울서초경찰서");
        addMarker(37.4718450, 127.0271792, "서울서초경찰서");
        addMarker(37.4549920, 127.0648545, "서울서초경찰서");

        addMarker(37.5666533, 127.0326493, "서울성동경찰서");
        addMarker(37.5574251, 127.0288558, "서울성동경찰서");
        addMarker(37.5449597, 127.0138463, "서울성동경찰서");
        addMarker(37.5547323, 127.0196132, "서울성동경찰서");
        addMarker(37.5497670, 127.0300234, "서울성동경찰서");
        addMarker(37.5647586, 127.0517402, "서울성동경찰서");

        addMarker(37.6160838, 127.0086109, "서울성북경찰서");
        addMarker(37.6045307, 127.0106875, "서울성북경찰서");
        addMarker(37.5932791, 126.9985215, "서울성북경찰서");

        addMarker(37.4969882, 127.1482966, "서울송파경찰서");
        addMarker(37.4851827, 127.1288810, "서울송파경찰서");
        addMarker(37.5287255, 127.1168629, "서울송파경찰서");
        addMarker(37.5068859, 127.1280993, "서울송파경찰서");
        addMarker(37.5201652, 127.1123922, "서울송파경찰서");
        addMarker(37.4778753, 127.1458547, "서울송파경찰서");

        addMarker(37.4891379, 127.1047973, "서울수서경찰서");
        addMarker(37.4651386, 127.1073750, "서울수서경찰서");

        addMarker(37.5320806, 126.8322435, "서울양천경찰서");
        addMarker(37.5149133, 126.8550058, "서울양천경찰서");
        addMarker(37.5390442, 126.8306805, "서울양천경찰서");

        addMarker(37.5227185, 126.8897017, "서울영등포경찰서");
        addMarker(37.4983297, 126.8980767, "서울영등포경찰서");
        addMarker(37.5151247, 126.9087325, "서울영등포경찰서");

        addMarker(37.5342949, 126.9944044, "서울용산경찰서");
        addMarker(37.5310164, 127.0062836, "서울용산경찰서");
        addMarker(37.5268368, 127.0001932, "서울용산경찰서");
        addMarker(37.5361157, 126.9747139, "서울용산경찰서");

        addMarker(37.6367356, 126.9183299, "서울은평경찰서");
        addMarker(37.6111425, 126.9289875, "서울은평경찰서");

        addMarker(37.5689572, 126.9881500, "서울종로경찰서");
        addMarker(37.6000351, 126.9584514, "서울종로경찰서");
        addMarker(37.6047609, 126.9654294, "서울종로경찰서");
        addMarker(37.5814059, 126.9811324, "서울종로경찰서");
        addMarker(37.5767705, 126.9740008, "서울종로경찰서");
        addMarker(37.5841381, 126.9705976, "서울종로경찰서");
        addMarker(37.5814725, 126.9694271, "서울종로경찰서");
        addMarker(37.5722606, 126.9611435, "서울종로경찰서");
        addMarker(37.5689364, 126.9764239, "서울종로경찰서");
        addMarker(37.5707510, 126.9722054, "서울종로경찰서");
        addMarker(37.5759361, 126.9672640, "서울종로경찰서");
        addMarker(37.5725619, 126.9789970, "서울종로경찰서");

        addMarker(37.6089690, 127.0552581, "서울종암경찰서");
        addMarker(37.6000864, 127.0312535, "서울종암경찰서");

        addMarker(37.5882137, 127.0939012, "서울중랑경찰서");
        addMarker(37.5831943, 127.0841512, "서울중랑경찰서");
        addMarker(37.5913390, 127.0728207, "서울중랑경찰서");
        addMarker(37.6146259, 127.0815386, "서울중랑경찰서");

        addMarker(37.5650079, 127.0163825, "서울중부경찰서");
        addMarker(37.5588159, 127.0048761, "서울중부경찰서");
        addMarker(37.5608522, 126.9915765, "서울중부경찰서");
        addMarker(37.5661811, 126.9928486, "서울중부경찰서");

        addMarker(37.5861068, 127.0014054, "서울혜화경찰서");
        addMarker(37.5785962, 127.0060393, "서울혜화경찰서");
        addMarker(37.5892964, 126.9976135, "서울혜화경찰서");
        addMarker(37.5784788, 127.0136459, "서울혜화경찰서");
        addMarker(37.5726524, 127.0170718, "서울혜화경찰서");
        addMarker(37.5759995, 127.0105240, "서울혜화경찰서");
        addMarker(37.5707326, 127.0016342, "서울혜화경찰서");
        addMarker(37.5729461, 127.0048693, "서울혜화경찰서");
    }

    private void addMarker(double latitude, double longitude, String title) {
        LatLng position = new LatLng(latitude, longitude);
        Bitmap markerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.custom_marker); // 사용자가 원하는 이미지 리소스로 변경
        Bitmap resizedMarkerBitmap = resizeMarkerBitmap(markerBitmap, 150, 150); // 원하는 크기로 조정
        BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromBitmap(resizedMarkerBitmap);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(position)
                .title(title)
                .icon(markerIcon);
        googleMap.addMarker(markerOptions);
    }

    private Bitmap resizeMarkerBitmap(Bitmap bitmap, int width, int height) {
        return Bitmap.createScaledBitmap(bitmap, width, height, false);
    }


    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(this)
                    .setTitle("위치 권한 요청")
                    .setMessage("현재 위치를 표시하기 위해 위치 권한이 필요합니다.")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
                        }
                    })
                    .create()
                    .show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // super 호출 추가

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한 허용됨
                updateCurrentLocation();
            } else {
                // 권한 거부됨
                Toast.makeText(this, "위치 권한이 거부되어 현재 위치를 표시할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateCurrentLocation() {
        if (googleMap == null) {
            return;
        }

        // 현재 위치 표시
        if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);

        // 현재 위치로 이동
        fusedLocationClient.getLastLocation().addOnSuccessListener(MapActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f));
                }
            }
        });
    }

    public void onCurrentLocationButtonClick(View view) {
        updateCurrentLocation();
    }
}