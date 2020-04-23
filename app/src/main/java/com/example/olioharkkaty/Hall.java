package com.example.olioharkkaty;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class Hall {

    public ArrayList<User> users;
    private ArrayList<String> rooms;
    private File file = new File("reservations.xml");

    private Hall() {
        rooms = new ArrayList<String>(Arrays.asList("Tennis 1", "Tennis 2", "Tennis 3", "Badminton 1", "Badminton 2"));
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

    public void makeReservation(String time, String room, String date) {
        Serializer ser = new Persister();
        try {
            ser.write(new Reservation("2.2.2020", "18.00", "pale", "Sulkapalloo pelailees"), file);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addUser(User u) {
        users.add(u);
    }
}
