package com.example.olioharkkaty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Spinner;

public class CalenderActivity extends AppCompatActivity {

    CalendarView calendarView;
    Spinner hallRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        calendarView = (CalendarView) findViewById(R.id.calendarView)
    }


}
