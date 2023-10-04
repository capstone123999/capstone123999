package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teenager_main);

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
}
