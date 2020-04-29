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

        try {
            Hall.getInstance().config(MainActivity.this);
        } catch (Exception e) {
            // config tiedoston parsiminen epäonnistui
            e.printStackTrace();
            finish();
            System.exit(0);
        }
    }

    public void openCalendarActivity(View v){
        Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
        startActivity(intent);
    }


    public void openUserInfoActivity(View v){
        Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
        startActivity(intent);
    }

    public void signOut(View v){
        UserManager.getInstance().setCurrentUser(null);
        finish();
    }
}