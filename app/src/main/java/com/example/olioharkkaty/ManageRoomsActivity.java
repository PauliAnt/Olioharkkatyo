package com.example.olioharkkaty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class ManageRoomsActivity extends AppCompatActivity {
    // Class is used to control admin room management activity
    private ArrayList<String> rooms;
    private Spinner hallroom;
    private TextView addRoom;
    private String roomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_rooms);

        Hall hall = Hall.getInstance();
        rooms = hall.getRooms();
        hallroom = (Spinner) findViewById(R.id.spinner2);
        addRoom = (TextView) findViewById(R.id.addRoom);

        // Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rooms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hallroom.setAdapter(adapter);
    }

    public void deleteRoomFromList(View v){
        roomName = hallroom.getSelectedItem().toString();
        // todo lisää toiminnallisuus poista huone listasta
    }

    public void addRoomToList(View v){
        roomName = addRoom.getText().toString();
        // todo lisää toiminnallisuus lisää huoneen huonelistaan
    }

    public void backButton(View v){ finish();}
}
