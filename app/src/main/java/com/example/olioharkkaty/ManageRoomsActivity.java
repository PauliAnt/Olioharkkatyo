package com.example.olioharkkaty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ManageRoomsActivity extends AppCompatActivity {
    // Class is used to control admin room management activity
    private ArrayList<String> rooms;
    private Spinner hallroom;
    private EditText room,roomid;
    private Toast errormessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_rooms);

        Hall hall = Hall.getInstance();
        hallroom = (Spinner) findViewById(R.id.spinner2);
        room = (EditText)findViewById(R.id.addRoom);
        roomid = (EditText)findViewById(R.id.addRoomId);
        refreshSpinner();

    }

    public void deleteRoomFromList(View v){
        Hall.getInstance().deleteRoom(hallroom.getSelectedItem().toString());
        refreshSpinner();
    }

    public void addRoomToList(View v){
        try {
            if(!Hall.getInstance().addRoom(room.getText().toString(),Integer.parseInt(roomid.getText().toString()))) {
                errormessage = Toast.makeText(ManageRoomsActivity.this, "Id or room already taken",Toast.LENGTH_SHORT);
                errormessage.show();
            }

        } catch (NumberFormatException e) {
            errormessage = Toast.makeText(ManageRoomsActivity.this,"Invalid id", Toast.LENGTH_SHORT);
            errormessage.show();
        }
        refreshSpinner();


    }

    public void backButton(View v){ finish();}

    private void refreshSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Hall.getInstance().getRooms());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hallroom.setAdapter(adapter);
    }
}
