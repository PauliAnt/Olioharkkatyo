package com.example.olioharkkaty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openCalendarActivity(View v){
        Intent intent = new Intent(MainActivity.this,CalenderActivity.class);
        startActivity(intent);
    }

}