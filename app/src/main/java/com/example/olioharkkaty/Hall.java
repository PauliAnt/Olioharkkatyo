package com.example.olioharkkaty;

import java.util.ArrayList;
import java.util.Arrays;

public class Hall {

    private ArrayList<String> rooms;

    private Hall(){
        rooms = new ArrayList<String>(Arrays.asList("Tennis 1","Tennis 2","Tennis 3","Badminton 1","Badminton 2"));
    }

    public static Hall getInstance(){return new Hall();}

    public ArrayList<String> getRooms(){return rooms;};

    public ArrayList<String> getAvailableReservations(int roomID, String date){
        return null;
    }

    public void makeReservation(int time, int roomID, String date){}

}
