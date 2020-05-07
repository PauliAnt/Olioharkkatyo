package com.example.olioharkkaty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class AdminViewActivity extends AppCompatActivity {
    // Class is used to control admin room management activity
    private ArrayList<String> rooms;
    private Spinner hallroom;
    private EditText room,roomid;
    private Toast errormessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);

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
            if(Hall.getInstance().addRoom(room.getText().toString(),Integer.parseInt(roomid.getText().toString()))) {
                room.getText().clear();
                roomid.getText().clear();
            } else {
                errormessage = Toast.makeText(AdminViewActivity.this, "ID or room already taken",Toast.LENGTH_SHORT);
                errormessage.show();
            }

        } catch (NumberFormatException e) {
            // ID is not integer
            errormessage = Toast.makeText(AdminViewActivity.this,"Invalid ID", Toast.LENGTH_SHORT);
            errormessage.show();
        }

        refreshSpinner();


    }

    public void resetProgress(View v) {
            File file = getFilesDir();
            File[] files = file.listFiles();
                if (files != null) {
                    for (File f : files) {
                        f.delete();
                    }
                }
            getSharedPreferences("Users", Context.MODE_PRIVATE).edit().clear().commit();
        Toast.makeText(this,"Progress reset successful",Toast.LENGTH_SHORT);
    }


    public void backButton(View v){ finish();}

    private void refreshSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Hall.getInstance().getRooms());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hallroom.setAdapter(adapter);
    }
}
