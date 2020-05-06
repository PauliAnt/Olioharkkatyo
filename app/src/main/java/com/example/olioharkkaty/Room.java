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

    @ElementList(entry = "reservation",inline = true, type = Reservation.class, required = false)
    private List<Reservation> reservations;

    @Element
    private String name;

    @ElementList(entry = "regularReservation",inline = true, type = RegularReservation.class, required = false)
    private List<RegularReservation> regularReservations;

    @Element
    private int nextid; // reservation id contains room id part and index of reservation part



    public Room(String name,int id) {
        this.name = name;
        this.id = id;
        reservations = new ArrayList<Reservation>();
        regularReservations = new ArrayList<RegularReservation>();
        nextid = 1000*id+1;
    }


    public String getName() {
        return name;
    }

    public int addReservation(String date, String time, String description, int sportid) {
        int reservationid = nextid;
        nextid++;
        if (reservations == null)
            reservations = new ArrayList<Reservation>();
        reservations.add(new Reservation(date, time, UserManager.getInstance().getCurrentUserName(), description, sportid, reservationid, name));
        return reservationid;
    }

    public int addRegularReservation(int weekday, String time, String describtion, int sportid){
        int reservationid = nextid;
        nextid++;
        if(regularReservations == null)
            regularReservations = new ArrayList<RegularReservation>();
        regularReservations.add(new RegularReservation(time,weekday,UserManager.getInstance().getCurrentUserName(),describtion,sportid,reservationid,name));
        return reservationid;
    }

    public ArrayList<String> getAvailableHours(String date, int openinghour, int closinghour) {
        // Returns list of time strings by date, and open hours parameters
        ArrayList<String> reservedhours = new ArrayList<String>(), availablehours = new ArrayList<String>();
        // Finding reserved slots
        if(reservations != null) {
            for (Reservation reservation : reservations) {
                if (reservation.getDate().equals(date))
                    reservedhours.add(reservation.getTime());

            }
        }
        // Taking non reserved slots and returning them
        for (int hour = openinghour; hour < closinghour; hour++) {
            String time = String.format("%02d.00",hour);
            if(!reservedhours.contains(time)){
                availablehours.add(time);
            }
        }

        return availablehours;
    }

    public ArrayList<String> getAvailableRegularHours(int weekday, int openinghour, int closinghour) {
        // Returns list of time strings by date, and open hours parameters
        ArrayList<String> reservedhours = new ArrayList<String>(), availablehours = new ArrayList<String>();
        // Finding reserved slots
        if (regularReservations != null) {
            for (RegularReservation reservation : regularReservations) {
                if (reservation.getWeekday() == weekday)
                    reservedhours.add(reservation.getTime());

            }
        }
        // Taking non reserved slots and returning them
        for (int hour = openinghour; hour < closinghour; hour++) {
            String time = String.format("%02d.00",hour);
            if(!reservedhours.contains(time)){
                availablehours.add(time);
            }
        }
        return availablehours;
    }

    public boolean isReserved(String date, String time){
        if(reservations == null)
            return false;
        for (Reservation reservation:reservations){
            if (reservation.getDate().equals(date) && reservation.getTime().equals(time))
                return true;
        }
        return false;
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
