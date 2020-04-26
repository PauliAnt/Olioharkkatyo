package com.example.olioharkkaty;


import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;


@Root
public class Room {

    @Attribute
    private int id;

    @ElementList(inline = true)
    private List<Reservation> reservations;

    @Element
    private String name;

    @Element
    private int nextid;

    // Tyhjä rakentaja Simple kirjastoa varten
    private Room() {}


    public Room(String name,int id) {
        this.name = name;
        this.id = id;
        reservations = new ArrayList<Reservation>();
        nextid = 1000*id+1;
    }


    public String getName() {
        return name;
    }

    public int addReservation(String date, String time, String describtion, String sport) {
        int reservationid = nextid;
        nextid++;
        reservations.add(new Reservation(date, time, "pale", describtion, sport, reservationid));
        return reservationid;
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
