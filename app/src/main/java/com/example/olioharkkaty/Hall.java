package com.example.olioharkkaty;


import android.content.Context;
import android.util.Log;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;


public class Hall {
    /*  Luokka käsittelee varausjärjestelmän toiminnallisuutta Room-luokkaa apuna käyttäen. */
    private HashMap<Integer,String> rooms;
    private String fname = "reservations.xml";
    private String[] sports;
    private int[][] openhours;
    private Context con;


    private Hall() {
        rooms = new HashMap<Integer, String>();
    }

    public static final Hall instance = new Hall();

    public static Hall getInstance() {return instance;}



    public void config(Context con) throws Exception {
        this.con = con;
        // Etsii config asetukset puhelimen asutuksista
        String filename = "hallconfig.xml";
        InputStream is = null;
        HallConfig hc = null;
        try {
            is = con.openFileInput(filename);
            hc = deserializeXMLToConfigObject(is);
            is.close();
        } catch (IOException e){
            // Luetaan assets kansiosta config ja tallennetaan puhelimen muistiin
            is = con.getAssets().open(filename);
            hc = deserializeXMLToConfigObject(is);
            is.close();
            this.serializeObjectToXML(filename,hc);


        }
        openhours = hc.getOpenHours();
        sports = hc.getSports();
        rooms = hc.getRoomMap();
        UserManager.getInstance().setAdmin(hc.getAdmin());
        Log.i("config",Integer.toString(rooms.size()));
    }

    public ArrayList<String> getRooms() {
        ArrayList<String> al = new ArrayList<String>();
        al.addAll(rooms.values());
        Log.i("getRooms",Integer.toString(rooms.size())+ Integer.toString(rooms.keySet().size()));
        return al;
    }
    public String[] getSports() { return sports; }

    public ArrayList<String> getAvailableReservations(Context con, String roomname, String date) throws ParseException {

        // Viikonpäivän parsiminen ja aukioloajat
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyy");
        c.setTime(sdf.parse(date));
        int day = c.get(Calendar.DAY_OF_WEEK);

        int openinghour = openhours[day - 1][0];
        int closinghour = openhours[day - 1][1];

        // Luodaan Arraylist joka voidaan palauttaa jos tiedostoa ei löydy
        ArrayList<String> al = new ArrayList<String>();
        for (int hour = openinghour; hour <= closinghour; hour++) {
            al.add(String.format("%02d.00", hour));
        }

        try {
            // Room objektin luku tiedostosta
            Room room = deserializeXMLToRoomObject(roomname);
            return (room.getAvailableHours(date, openinghour, closinghour));


        } catch (FileNotFoundException e) {
            // Tiedostoa ei ole -> palautetaan kaikki ajat
            return (al);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void makeReservation(Context con, String time, String roomname, String date, String describtion, int sportid) {
        Room room = null;
        try {
            // luetaan vanhat varaukset
            room = deserializeXMLToRoomObject(roomname);

        } catch (FileNotFoundException e) {
            room = new Room(roomname,(int)this.getKeyFromValue(rooms,roomname));

        } catch (Exception e) {
            e.printStackTrace();

        }
        // Lisätään uusi varaus
        int id = room.addReservation(date, time, describtion, sportid);
        UserManager.getInstance().addReservationid(con, id);

        try {
            //kirjoitetaan varaukset tiedostoon
            String filename = room.getName().replaceAll(" ", "") + fname;
            serializeObjectToXML(filename, room);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Reservation> findReservationsByIdList(ArrayList<Integer> ids) {
        ArrayList<Reservation> reservations = new ArrayList<Reservation>();
        Collections.sort(ids);
        int roomid = 0;
        Room room = null;
        Iterator<Integer> itr = ids.iterator();
        int id = itr.next();
        try {
            while (true) {
                if (id / 1000 == roomid) {
                    reservations.add(room.getReservationById(id));
                    if (itr.hasNext())
                        id = itr.next();
                    else
                        break;
                } else {
                    roomid = id / 1000;
                    room = deserializeXMLToRoomObject(rooms.get(roomid));
                }
            }
        } catch(Exception e) {
            // Parsiminen epäonnistui
            e.printStackTrace();
            return null;
        }

        return reservations;
    }


    private Room deserializeXMLToRoomObject(String roomname) throws Exception {
        String filename = roomname.replaceAll(" ", "") + fname;
        InputStream is = null;
        is = con.openFileInput(filename);
        Serializer ser = new Persister();
        Room room = ser.read(Room.class, is);
        is.close();
        return room;
    }

    private HallConfig deserializeXMLToConfigObject(InputStream is) throws Exception {
        String filename = "hallconfig.xml";
        Serializer ser = new Persister();
        HallConfig hc = ser.read(HallConfig.class, is);
        return hc;
    }

    private <Object> void serializeObjectToXML(String filename, Object object) throws Exception {
        OutputStream os = con.openFileOutput(filename, Context.MODE_PRIVATE);
        Serializer ser = new Persister();
        ser.write(object, os);
        os.close();

    }

    private Object getKeyFromValue(HashMap map, String value) {
        for (Object key:map.keySet()){
            if (map.get(key).equals(value))
                    return(key);
        }
        return null;
    }
}

