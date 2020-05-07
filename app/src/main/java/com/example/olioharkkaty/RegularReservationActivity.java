package com.example.olioharkkaty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RegularReservationActivity extends AppCompatActivity {

    private Spinner roomspinner, timespinner, sportspinner, weekdayspinner, datespinner;
    private EditText description;
    private ArrayList<String> availableslots;
    private Button confirm;
    private String roomname, sport;
    private int weekday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_reservation);
        String[] weekdays = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};

        final Hall hall = Hall.getInstance();
        roomspinner = findViewById(R.id.roomSpinner);
        weekdayspinner = findViewById(R.id.weekdaySpinner);
        timespinner = findViewById(R.id.availabletimesSpinner);
        sportspinner = findViewById(R.id.sportSpinner);
        description = findViewById(R.id.description);
        confirm = findViewById(R.id.confirm);

        ArrayAdapter<String> spinneradapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,hall.getRooms());
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomspinner.setAdapter(spinneradapter);

        spinneradapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,weekdays);
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weekdayspinner.setAdapter(spinneradapter);

        spinneradapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,hall.getSports());
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportspinner.setAdapter(spinneradapter);

        datespinner = findViewById(R.id.datespinner);

        timespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(RegularReservationActivity.this,android.R.layout.simple_spinner_item,hall.findNextAvailableDays(roomname,weekday,timespinner.getSelectedItem().toString(),null));
                spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                datespinner.setAdapter(spinneradapter);
                confirm.setEnabled(true);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


    public void findAvailableTimes(View v) {
        availableslots = Hall.getInstance().getAvailableRegularReservations(roomspinner.getSelectedItem().toString(),getWeekday());
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,availableslots);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timespinner.setAdapter(adapter);
        roomname = roomspinner.getSelectedItem().toString();
        sport = sportspinner.getSelectedItem().toString();
        weekday = getWeekday();
    }

    public void addReservation(View v){
        Hall.getInstance().makeRegularReservation(timespinner.getSelectedItem().toString(),roomspinner.getSelectedItem().toString(),getWeekday(),description.getText().toString(),sportspinner.getSelectedItemPosition(),datespinner.getSelectedItem().toString());
        finish();
    }

    private int getWeekday(){
        int day = weekdayspinner.getSelectedItemPosition();
        if (day != 6)
            day++;
        else
            day = 0;
        return day;
    }
}
