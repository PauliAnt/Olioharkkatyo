package com.example.olioharkkaty;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class Hall {

    public ArrayList<User> users;
    private ArrayList<String> rooms;

    private Hall(){
        rooms = new ArrayList<String>(Arrays.asList("Tennis 1","Tennis 2","Tennis 3","Badminton 1","Badminton 2"));
    }

    public static Hall getInstance(){return new Hall();}

    public ArrayList<String> getRooms(){return rooms;};

    public ArrayList<String> getAvailableReservations(String room, String date){
        return (new ArrayList<String>(Arrays.asList("12.00","13.00","14.00","15.00","19.00")));
    }

    public void makeReservation(int time, String room, String date){
        try {
            FileOutputStream fos = new FileOutputStream("reservations.xml");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void addUser(User u) {
        users.add(u);
    }
}
