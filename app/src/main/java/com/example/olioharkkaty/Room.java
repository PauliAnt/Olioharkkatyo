package com.example.olioharkkaty;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;


@Root
public class Room {

    @ElementList(inline=true)
    private List<Reservation> reservations;

    @Element
    private String name;

    // Tyhjä rakentaja Simple kirjastoa varten
    private Room(){}


    public Room(String n) {
        name = n;
        reservations = new ArrayList<Reservation>();
    }



    public String getName(){
        return name;
    }

    public void addReservation(String date, String time,String describtion, String sport) {
        reservations.add(new Reservation(date, time, "pale", describtion,sport));
    }

    public boolean isReserved(String time, String date) {
        for (Reservation res:reservations){
            Log.i("isReserved()",time + "  " +res.getTime()+"\n"+date + "  " + res.getDate());
            if (res.getTime().equals(time) && res.getDate().equals(date)){
                return true;
            }
        }
        return false;
    }
}
