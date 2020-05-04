package com.example.olioharkkaty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;

public class ReservationActivity extends AppCompatActivity {

    private String room;
    private String date;
    private TextView dateView, roomView;
    private Spinner availableSlots, sports;
    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        date = getIntent().getStringExtra("date");
        room = getIntent().getStringExtra("room");
        dateView = (TextView)findViewById(R.id.dateView);
        roomView = (TextView)findViewById(R.id.roomView);
        availableSlots = (Spinner)findViewById(R.id.availableSlotsSpinner);
        sports = (Spinner)findViewById(R.id.sportSpinner);
        description = (EditText)findViewById(R.id.editDescribtion);


        dateView.setText(date);
        roomView.setText(room);

        Hall hall = Hall.getInstance();

        ArrayAdapter<String> adapter = null;
        ArrayList<String> reservations = hall.getAvailableReservations(room,date);
        if(reservations == null)
            // Ongelma kalenterin käytössä tai xml parsimisessa
            System.exit(1);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,reservations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        availableSlots.setAdapter(adapter);


        adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,hall.getSports());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sports.setAdapter(adapter);

    }

    public void backButton(View v){
        finish();
    }

    public void makeReservation(View V) {
        Hall.getInstance().makeReservation(ReservationActivity.this, availableSlots.getSelectedItem().toString(), room, date, description.getText().toString(), sports.getSelectedItemPosition());
        finish();
    }
}
