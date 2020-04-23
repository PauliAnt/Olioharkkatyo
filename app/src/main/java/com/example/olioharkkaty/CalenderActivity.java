package com.example.olioharkkaty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;

import java.util.ArrayList;

public class CalenderActivity extends AppCompatActivity {

    CalendarView calendarView;
    Spinner hallRoom;
    ArrayList<String> rooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        hallRoom = (Spinner) findViewById(R.id.spinner);

        Hall hall = Hall.getInstance();
        rooms = hall.getRooms();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rooms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hallRoom.setAdapter(adapter);
    }


}
