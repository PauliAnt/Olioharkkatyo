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
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private Spinner hallRoom;
    private ArrayList<String> rooms;
    private TextView textView;
    private String date;
    private Toast invalidDate;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        textView = (TextView) findViewById(R.id.textView2);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        hallRoom = (Spinner) findViewById(R.id.spinner);

        Hall hall = Hall.getInstance();
        rooms = hall.getRooms();
        invalidDate = Toast.makeText(CalendarActivity.this,"You can't make reservation for that day",Toast.LENGTH_SHORT);

        // Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rooms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hallRoom.setAdapter(adapter);

        // Initiating date String with current date
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

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_MONTH,-1);
        try {
            // Check if date is before current day
            if (sdf.parse(date).compareTo(now.getTime()) < 0){
                invalidDate.show();
            } else {
                Intent intent = new Intent(CalendarActivity.this, ReservationActivity.class);
                intent.putExtra("date", date);
                intent.putExtra("room", hallRoom.getSelectedItem().toString());
                startActivity(intent);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void mainMenu(View v){
        finish();
    }

}
