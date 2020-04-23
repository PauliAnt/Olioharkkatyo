package com.example.olioharkkaty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private Spinner hallRoom;
    private ArrayList<String> rooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        hallRoom = (Spinner) findViewById(R.id.spinner);

        Hall hall = Hall.getInstance();
        rooms = hall.getRooms();

        // Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rooms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hallRoom.setAdapter(adapter);
    }
    public void openReservationActivity(View v){
        Intent intent = new Intent(CalendarActivity.this, ReservationActivity.class);
        SimpleDateFormat sdf  = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(new Date(calendarView.getDate()));
        intent.putExtra("date",date); // todo palauttaa aina kyseisen päivän. korjattava
        intent.putExtra("room",hallRoom.getSelectedItem().toString());
        startActivity(intent);

    }


}
