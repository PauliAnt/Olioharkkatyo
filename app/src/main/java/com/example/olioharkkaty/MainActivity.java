package com.example.olioharkkaty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    protected String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = getIntent().getStringExtra("Username");
    }

    public void openCalendarActivity(View v){
        Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
        startActivity(intent);
    }


    public void openUserInfoActivity(View v){
        Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
        intent.putExtra("Username", username);
        startActivity(intent);
    }
}