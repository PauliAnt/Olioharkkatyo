package com.example.olioharkkaty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        try {
            Hall.getInstance().config(AdminMainActivity.this);
        } catch (Exception e) {
            // config tiedoston parsiminen epäonnistui
            e.printStackTrace();
            finish();
            System.exit(0);
        }
    }

    public void openCalendarActivity1(View v){
        Intent intent = new Intent(AdminMainActivity.this, CalendarActivity.class);
        startActivity(intent);
    }

    public void openMyReservationsActivity1(View v){
        Intent intent = new Intent(AdminMainActivity.this, MyReservationsActivity.class);
        startActivity(intent);
    }


    public void openUserInfoActivity1(View v){
        Intent intent = new Intent(AdminMainActivity.this, UserInfoActivity.class);
        startActivity(intent);
    }

    public void openManageRoomsActivity1(View v){
        Intent intent = new Intent(AdminMainActivity.this, ManageRoomsActivity.class);
        startActivity(intent);
    }

    public void signOut1(View v){
        UserManager.getInstance().setCurrentUser(null);
        finish();
    }

}
