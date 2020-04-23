package com.example.olioharkkaty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class ReservationActivity extends AppCompatActivity {

    // todo lisää toiminnallisuus spinnereille. Ylempi vapaille vuoroille. alempi josta voi valita lajin (1 lisäpiste)

    private String room;
    private String date;
    private TextView dateView, roomView;
    private Spinner availableSlots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        date = getIntent().getStringExtra("date");
        room = getIntent().getStringExtra("room");
        dateView = (TextView)findViewById(R.id.dateView);
        roomView = (TextView)findViewById(R.id.roomView);
        availableSlots = (Spinner)findViewById(R.id.availableSlotsSpinner);

        dateView.setText(date);
        roomView.setText(room);
        Hall hall = Hall.getInstance();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,hall.getAvailableReservations(date,room));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        availableSlots.setAdapter(adapter);
    }

    public void backButton(View v){
        finish();
    }

    public void makeReservation(View V) {
        Hall.getInstance().makeReservation(availableSlots.getSelectedItem().toString(),room,date);
    }
}
