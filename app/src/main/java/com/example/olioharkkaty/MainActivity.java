package com.example.olioharkkaty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Class is used to handle main menu
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout constraintLayout = findViewById(R.id.constrainLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

    }

    public void openCalendarActivity(View v){
        Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
        startActivity(intent);
    }

    public void openRegularReservationActivity(View v){
        Intent intent = new Intent(MainActivity.this,RegularReservationActivity.class);
        startActivity(intent);
    }

    public void openMyReservationsActivity(View v){
        Intent intent = new Intent(MainActivity.this, MyReservationsActivity.class);
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