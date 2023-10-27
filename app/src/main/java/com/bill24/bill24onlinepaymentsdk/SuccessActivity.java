package com.bill24.bill24onlinepaymentsdk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class SuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        String data= getIntent().getStringExtra("MyData");


        Log.d("llll", "onCreate: "+data);
    }
}