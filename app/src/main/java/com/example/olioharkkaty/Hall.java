package com.example.olioharkkaty;


import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;


public class Hall {
    // Class has all methods for handling reservation system.
    private HashMap<Integer,String> rooms;
    private String fname = "reservations.xml";
    private String configfilename = "hallconfig.xml";
    private String[] sports;
    private int[][] openhours;
    private Context con;

    // Singleton pattern
    private Hall() {
        rooms = new HashMap<Integer, String>();
    }
    public static final Hall instance = new Hall();
    public static Hall getInstance() {return instance;}


    public ArrayList<String> getRooms() {
        ArrayList<String> al = new ArrayList<String>();
        al.addAll(rooms.values());
        Collections.sort(al);
        return al;
    }
    public String[] getSports() { return sports; }


    public void config(Context con) throws Exception {
        // Finds configuration settings from files and sets context for other methods to use
        this.con = con;
        String filename = "hallconfig.xml";
        InputStream is = null;
        HallConfig hc = null;
        try {
            is = con.openFileInput(filename);
            hc = deserializeXMLToConfigObject(is);
            is.close();
        } catch (IOException e){
            // Reads assets from folder config and save to phones memory
            is = con.getAssets().open(filename);
            hc = deserializeXMLToConfigObject(is);
            is.close();
            this.serializeObjectToXML(filename,hc);


        }
        openhours = hc.getOpenHours();
        sports = hc.getSports();
        rooms = hc.getRoomMap();
        UserManager.getInstance().setAdmin(hc.getAdmin());
    }



    public ArrayList<String> getAvailableReservations(String roomname, String date) {
        // Returns list of time strings (formatted as hh.mm) corresponding room and date
        Calendar c = null, now = Calendar.getInstance();
        try {
            // finding correct weekday for the opening hour and closing hour.
            c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyy");
            c.setTime(sdf.parse(date));

        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }

        int day = c.get(Calendar.DAY_OF_WEEK);
        int openinghour = openhours[day - 1][0];
        int closinghour = openhours[day - 1][1];
        if ((c.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR))&&(c.get(Calendar.YEAR)==now.get(Calendar.YEAR)))
            openinghour = now.get(Calendar.HOUR_OF_DAY)+1;


        // Creating arraylist that can be returned if no reservations are found
        ArrayList<String> al = new ArrayList<String>();
        for (int hour = openinghour; hour < closinghour; hour++) {
            al.add(String.format("%02d.00", hour));
        }

        try {
            Room room = deserializeXMLToRoomObject(roomname);
            return (room.getAvailableHours(date, openinghour, closinghour));


        } catch (FileNotFoundException e) {
            // No reservations found
            return (al);
        } catch (Exception e) {
            // Error with XML parse
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public ArrayList<String> getAvailableRegularReservations(String roomname, int day) {
        // Returns list of available time strings (formatted as hh.mm) corresponding room and weekday
        // Openhour array is formatted to start indexing from sunday

        int openinghour = openhours[day][0];
        int closinghour = openhours[day][1];


        // Creating arraylist that can be returned if no reservations are found
        ArrayList<String> al = new ArrayList<String>();
        for (int hour = openinghour; hour < closinghour; hour++) {
            al.add(String.format("%02d.00", hour));
        }

        try {
            Room room = deserializeXMLToRoomObject(roomname);
            return (room.getAvailableRegularHours(day, openinghour, closinghour));


        } catch (FileNotFoundException e) {
            // No reservations found
            return (al);
        } catch (Exception e) {
            // Error with XML parse
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }



    public void makeReservation(String time, String roomname, String date, String description, int sportid) {
        // Creates new reservation object and adds it to XML file
        Room room = null;
        try {
            // Reading old reservations from roomnamereservations.XML file
            room = deserializeXMLToRoomObject(roomname);

        } catch (FileNotFoundException e) {
            // No file found. Creating new room object instead
            room = new Room(roomname,this.getKeyFromValue(rooms,roomname));

        } catch (Exception e) {
            // Error with XML parse
            e.printStackTrace();
            System.exit(1);

        }
        // Adding new reservation with given parameters
        int id = room.addReservation(date, time, description, sportid);
        UserManager.getInstance().addReservationid(id);

        try {
            // Writing room object to XML with new reservation
            String filename = room.getName().replaceAll(" ", "") + fname;
            serializeObjectToXML(filename, room);

        } catch (Exception e) {
            // Error with XML parse
            e.printStackTrace();
            System.exit(1);
        }

    }
    public void makeRegularReservation(String time, String roomname, int weekday, String description, int sportid, String firstdate) {
        // Creates new reservation object and adds it to XML file
        Room room = null;
        try {
            // Reading old reservations from roomnamereservations.XML file
            room = deserializeXMLToRoomObject(roomname);

        } catch (FileNotFoundException e) {
            // No file found. Creating new room object instead
            room = new Room(roomname,getKeyFromValue(rooms,roomname));

        } catch (Exception e) {
            // Error with XML parse
            e.printStackTrace();
            System.exit(1);

        }
        // Adding new reservation with given parameters
        int id = room.addRegularReservation(weekday, time, description, sportid,firstdate);
        UserManager.getInstance().addReservationid(id);

        try {
            // Writing room object to XML with new reservation
            String filename = room.getName().replaceAll(" ", "") + fname;
            serializeObjectToXML(filename, room);

        } catch (Exception e) {
            // Error with XML parse
            e.printStackTrace();
            System.exit(1);
        }

    }

    public ArrayList<String> findNextAvailableDays(String roomname, int weekday, String time, String date) {
         // Finds 3 next available date strings for regular reservation by given room, weekday and time
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy"),sdf2 = new SimpleDateFormat("dd.MM.yyyyHH.mm");


        // Setting calendar for today
        Calendar now = Calendar.getInstance(),calendar = Calendar.getInstance();
        ArrayList<String> dates = new ArrayList<>();
        Room room = null;


        try {
            // If given null use current date
            if (date == null)
                calendar = now;
            else {
                calendar.setTime(sdf1.parse(date));
                // if given date is before current date -> use current date
                if (calendar.compareTo(now) < 0)
                    calendar = now;
            }
            room = deserializeXMLToRoomObject(roomname);

        } catch (IOException e) {
            // Making temporary room object that isn't reserved if no file is found
            room = new Room("temp",-1);
        } catch (Exception e) {
            // Error with XML or date parse
            e.printStackTrace();
            System.exit(1);
        }
        String newdate;
        while (dates.size() < 3) {
            if (calendar.get(Calendar.DAY_OF_WEEK) == weekday + 1) {
                newdate = sdf1.format(calendar.getTime());
                if (!room.isReserved(newdate, time))
                    dates.add(newdate);
            }
            calendar.add(Calendar.DATE, 1); }
        return dates;



    }

    public ArrayList<Reservation> findReservationsByIdList(ArrayList<Integer> ids) {
        // Method takes list of reservation ids and returns corresponding list of reservations
        ArrayList<Reservation> reservations = new ArrayList<>(), regularreservations = new ArrayList<>();
        // Sorting ids to make sure that reservations in the same room are subsequent
        Collections.sort(ids);

        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyyHH.mm");
        Reservation reservation;
        int roomid = 0;
        Room room = null;
        if (ids.size() == 0)
            return null;
        Iterator<Integer> itr = ids.iterator();
        int id = itr.next();
        try {
            while (true) {
                // Finding all reservations in the same room before reading the new file
                if (id / 1000 == roomid) {
                    reservation = room.getReservationById(id);
                    if (reservation instanceof RegularReservation)
                        regularreservations.add(reservation);
                    // Only add reservations from current day and future
                    else if(sdf.parse(reservation.getDate() + reservation.getTime()).compareTo(now.getTime()) > 0)
                        reservations.add(reservation);

                    if (itr.hasNext())
                        id = itr.next();
                    else
                        break;
                } else {
                    // Moving to next room
                    roomid = id / 1000;
                    room = deserializeXMLToRoomObject(rooms.get(roomid));
                }
            }
        } catch(Exception e) {
            // Error with XML parse
            e.printStackTrace();
            System.exit(1);
        }

        Collections.sort(reservations);
        Collections.sort(regularreservations);
        reservations.addAll(0,regularreservations);
        return reservations;
    }

    public void editReservation(Reservation reservation) {
        /* this method takes edited reservation object as parameter
        and finds and replaces the old one by unchanged id*/
        try {
            Room room = deserializeXMLToRoomObject(reservation.getRoom());
            room.removeReservation(reservation.getId());
            room.addReservation(reservation);
            serializeObjectToXML(reservation.getRoom().replaceAll(" ", "") + fname,room);

        } catch (Exception e) {
            // Error with XML parse
            e.printStackTrace();
            System.exit(1);
        }

    }

    public void removeReservation(Reservation reservation) {
        try {
            Room room = deserializeXMLToRoomObject(reservation.getRoom());
            room.removeReservation(reservation.getId());
            String filename = reservation.getRoom().replaceAll(" ","") + fname;
            if (room.getAmountOfReservations()==0)
                new File(con.getFilesDir(),filename).delete();
            else
                serializeObjectToXML(filename,room);
        } catch (Exception e) {
            // Error with XML parse
            e.printStackTrace();
            System.exit(1);
        }
    }

    public boolean addRoom(String roomname, int id) {
        if(rooms.keySet().contains(id) || rooms.values().contains(roomname))
            return false;

        try {
            InputStream is = con.openFileInput(configfilename);
            HallConfig hc = deserializeXMLToConfigObject(is);
            is.close();
            hc.addRoom(roomname,id);
            rooms = hc.getRoomMap();
            serializeObjectToXML(configfilename,hc);
        } catch (Exception e) {
            // Error with XML parse
            e.printStackTrace();
            System.exit(1);
        }
        return true;

    }

    public void deleteRoom(String roomname){
        try {
            InputStream is = con.openFileInput(configfilename);
            HallConfig hc = deserializeXMLToConfigObject(is);
            is.close();
            hc.deleteRoom(roomname);
            rooms = hc.getRoomMap();
            serializeObjectToXML(configfilename,hc);

        } catch (Exception e) {
            // Error wit XML parse
            e.printStackTrace();
            System.exit(1);
        }
    }

    private Room deserializeXMLToRoomObject(String roomname) throws Exception {
        // Deserialize XML to Room object using SimpleXML library
        String filename = roomname.replaceAll(" ", "") + fname;
        InputStream is = con.openFileInput(filename);
        Serializer ser = new Persister();
        Room room = ser.read(Room.class, is);
        is.close();
        return room;
    }

    private HallConfig deserializeXMLToConfigObject(InputStream is) throws Exception {
        // Takes Input stream as parameter so it can be used to read assets folder too
        Serializer ser = new Persister();
        HallConfig hc = ser.read(HallConfig.class, is);
        return hc;
    }

    private <Object> void serializeObjectToXML(String filename, Object object) throws Exception {
        // Serializes Room object to XML using SimpleXML library
        OutputStream os = con.openFileOutput(filename, Context.MODE_PRIVATE);
        Serializer ser = new Persister();
        ser.write(object, os);
        os.close();

    }

    private int getKeyFromValue(HashMap map, String value) {
        for (Object key:map.keySet()){
            if (map.get(key).equals(value))
                    return((int)key);
        }
        return -1;
    }
}

