package com.example.android_open_mateo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        membuat button
        Button btn_volley = findViewById(R.id.btn_volley);
        Button btn_retrofit = findViewById(R.id.btn_retrofit);

        btn_volley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                buat method untuk pindah activity/layout
                Intent intent = new Intent(MainActivity.this, WheaterVollyActivity.class);
                startActivity(intent);
            }
        });

        btn_retrofit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WhaterRetrofitActivity.class);
                startActivity(intent);
            }
        });



    }
}