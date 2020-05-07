package com.example.olioharkkaty;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;


@Root
public class Room {
    // Class is used to manage reservations and room properties
    @Attribute
    private int roomid;

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
        this.roomid = id;
        reservations = new ArrayList<Reservation>();
        regularReservations = new ArrayList<RegularReservation>();
        nextid = 1000*roomid+1;
    }


    public String getName() {
        return name;
    }

    public int addReservation(String date, String time, String description, int sportid) {
        // Adds reservation with given parameters to list
        int reservationid = nextid;
        nextid++;
        if (reservations == null)
            reservations = new ArrayList<Reservation>();
        reservations.add(new Reservation(date, time, UserManager.getInstance().getCurrentUserName(), description, sportid, reservationid, name));
        return reservationid;
    }
    public void addReservation(Reservation reservation) {
        // Adds reservation to list depending on its type
        if (reservation instanceof  RegularReservation)
            regularReservations.add((RegularReservation)reservation);
        else
            reservations.add(reservation);
    }

    public int addRegularReservation(int weekday, String time, String describtion, int sportid, String firstdate){
        int reservationid = nextid;
        nextid++;
        if(regularReservations == null)
            regularReservations = new ArrayList<RegularReservation>();
        regularReservations.add(new RegularReservation(time,weekday,UserManager.getInstance().getCurrentUserName(),describtion,sportid,reservationid,name,firstdate));
        return reservationid;
    }

    public ArrayList<String> getAvailableHours(String date, int openinghour, int closinghour) {
        // Returns list of time strings by date, and open hours parameters
        ArrayList<String> reservedhours = new ArrayList<String>(), availablehours = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        // Finding reserved slots from default reservations
        if(reservations != null) {
            for (Reservation reservation : reservations) {
                if (reservation.getDate().equals(date))
                    reservedhours.add(reservation.getTime());

            }
        }
        Calendar calendar = null;
        int day = 0;
        try {
            calendar = Calendar.getInstance();
            calendar.setTime((sdf.parse((date))));
            day = calendar.get(Calendar.DAY_OF_WEEK)-1;
        } catch (ParseException e) {
            //Error with XML parse
            e.printStackTrace();
            System.exit(1);
        }
        // Finding reserved slots from Regular reservations
        if (regularReservations != null) {
            for (RegularReservation reservation : regularReservations) {
                if ((reservation.getWeekday() == day))
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
        ArrayList<String> reservedhours = new ArrayList(), availablehours = new ArrayList<String>();
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
        // checks if room is reserved at given time and date
        if(reservations == null)
            return false;
        for (Reservation reservation:reservations){
            if (reservation.getDate().equals(date) && reservation.getTime().equals(time))
                return true;
        }
        return false;
    }

    public Reservation getReservationById(int id){
        // Finds reservation by given id and returns it
        if (reservations != null) {
            for (Reservation reservation : reservations) {
                if (reservation.getId() == id)
                    return reservation;
            }
        }
        if (regularReservations != null) {
            for (RegularReservation reservation : regularReservations) {
                if (reservation.getId() == id)
                    return reservation;
            }
        }
        return null;
    }


    public void removeReservation(int id){
        Reservation reservation = getReservationById(id);
        if (reservations != null) {
            if (reservations.contains(reservation))
                reservations.remove(reservation);
        }
        if (regularReservations != null) {
            if (regularReservations.contains(reservation))
                regularReservations.remove(reservation);
        }
    }

    public int getAmountOfReservations(){
        int sum = 0;
        if(reservations != null)
            sum += reservations.size();
        if(regularReservations != null)
            sum += regularReservations.size();
        return sum;}


    // Empty builder for SimpleXML
    private Room() {}
}
