package com.example.olioharkkaty;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;


@Root
public class Room {

    @ElementList(inline = true)
    private List<Reservation> reservations;

    @Element
    private String name;

    // Tyhjä rakentaja Simple kirjastoa varten
    private Room() {}


    public Room(String n) {
        name = n;
        reservations = new ArrayList<Reservation>();
    }


    public String getName() {
        return name;
    }

    public void addReservation(String date, String time, String describtion, String sport) {
        reservations.add(new Reservation(date, time, "pale", describtion, sport));
    }

    public ArrayList<String> getAvailableHours(String date, int openinghour, int closinghour) {
        ArrayList<String> reservedhours = new ArrayList<String>(), availablehours = new ArrayList<String>();
        for (Reservation reservation : reservations) {
            if (reservation.getDate().equals(date)) {
                reservedhours.add(reservation.getTime());
            }
        }
        for (int hour = openinghour; hour <= closinghour; hour++) {
            String time = String.format("%02d.00",hour);
            if(!reservedhours.contains(time)){
                availablehours.add(time);
            }

        }

        return availablehours;
    }
}
