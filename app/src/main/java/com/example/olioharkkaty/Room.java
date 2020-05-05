package com.example.olioharkkaty;


import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;


@Root
public class Room {
    // Class is used to manage reservations and room properties
    @Attribute
    private int id;

    @ElementList(inline = true)
    private List<Reservation> reservations;

    @Element
    private String name;

    @Element
    private int nextid; // reservation id contains room id part and index of reservation part



    public Room(String name,int id) {
        this.name = name;
        this.id = id;
        reservations = new ArrayList<Reservation>();
        nextid = 1000*id+1;
    }


    public String getName() {
        return name;
    }

    public int addReservation(String date, String time, String describtion, int sportid) {
        int reservationid = nextid;
        nextid++;
        reservations.add(new Reservation(date, time, "pale", describtion, sportid, reservationid,name));
        return reservationid;
    }

    public ArrayList<String> getAvailableHours(String date, int openinghour, int closinghour) {
        // Returns list of time strings by date, and open hours parameters
        ArrayList<String> reservedhours = new ArrayList<String>(), availablehours = new ArrayList<String>();
        // Finding reserved slots
        for (Reservation reservation : reservations) {
            if (reservation.getDate().equals(date)) {
                reservedhours.add(reservation.getTime());
            }
        }
        // Taking non reserved slots and returning them
        for (int hour = openinghour; hour <= closinghour; hour++) {
            String time = String.format("%02d.00",hour);
            if(!reservedhours.contains(time)){
                availablehours.add(time);
            }

        }

        return availablehours;
    }

    public Reservation getReservationById(int id){
        for(Reservation reservation:reservations){
            if (reservation.getId()==id)
                return reservation;
        }
        return null;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public void removeReservation(int id){
        reservations.remove(getReservationById(id));
    }

    public int getAmountOfReservations(){return reservations.size();}


    // Empty builder for SimpleXML
    private Room() {}
}
