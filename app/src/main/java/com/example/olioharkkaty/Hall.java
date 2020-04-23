package com.example.olioharkkaty;

import android.content.Context;


import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class Hall {

    public ArrayList<User> users;
    private ArrayList<String> rooms;
    private String fname = "reservations.xml";

    private Hall() {
        rooms = new ArrayList<String>(Arrays.asList("Tennis 1", "Tennis 2", "Tennis 3", "Badminton 1", "Badminton 2"));
        users = new ArrayList<User>();

    }

    public static Hall getInstance() {
        return new Hall();
    }

    public ArrayList<String> getRooms() {
        return rooms;
    }


    public ArrayList<String> getAvailableReservations(String room, String date) {
        return (new ArrayList<String>(Arrays.asList("12.00", "13.00", "14.00", "15.00", "19.00")));
    }

    public void makeReservation(Context con,String time, String room, String date) {
        try {
            Serializer ser = new Persister();
            OutputStream os = con.openFileOutput(fname,Context.MODE_APPEND);
            // todo paranna XML käsittelyä
            ser.write(new Reservation(date, time, "pale", "Sulkapalloo pelailees"), os);
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addUser(String un, String pw) {
        User user = new User(un, pw);
        users.add(user);
    }
}
