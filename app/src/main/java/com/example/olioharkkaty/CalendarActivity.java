package com.example.olioharkkaty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private Spinner hallRoom;
    private ArrayList<String> rooms;
    private TextView textView;
    protected String date;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        textView = (TextView) findViewById(R.id.textView2);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        hallRoom = (Spinner) findViewById(R.id.spinner);

        Hall hall = Hall.getInstance();
        rooms = hall.getRooms();

        // Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rooms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hallRoom.setAdapter(adapter);

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        date = sdf.format(new Date(calendarView.getDate()));

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = String.format("%02d.%02d.%04d",dayOfMonth,month+1,year);
            }
        });
    }
    public void openReservationActivity(View v){

        Intent intent = new Intent(CalendarActivity.this, ReservationActivity.class);
        intent.putExtra("date", date);
        intent.putExtra("room", hallRoom.getSelectedItem().toString());
        startActivity(intent);
    }

    public void mainMenu(View v){
        finish();
    }

}
