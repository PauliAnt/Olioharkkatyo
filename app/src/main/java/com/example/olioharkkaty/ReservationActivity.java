package com.example.olioharkkaty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ReservationActivity extends AppCompatActivity {

    // todo lisää toiminnallisuus spinnereille. Ylempi vapaille vuoroille. alempi josta voi valita lajin (1 lisäpiste)

    private String room;
    private String date;
    private TextView dateView, roomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        date = getIntent().getStringExtra("date");
        room = getIntent().getStringExtra("room");
        dateView = (TextView)findViewById(R.id.dateView);
        roomView = (TextView)findViewById(R.id.roomView);
        dateView.setText(date);
        roomView.setText(room);
    }

    public void backButton(View v){
        Intent intent = new Intent(ReservationActivity.this, CalendarActivity.class);
        startActivity(intent);
    }
}
