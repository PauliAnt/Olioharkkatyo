package com.example.olioharkkaty;


import android.content.Context;
import android.util.Log;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;


public class Hall {

    public ArrayList<User> users;
    private ArrayList<String> rooms;
    private String fname = "reservations.xml";
    private int closinghour = 20;
    private int openinghour = 10;

    private Hall() {
        rooms = new ArrayList<String>(Arrays.asList("Tennis 1", "Tennis 2", "Tennis 3", "Badminton 1", "Badminton 2"));
        users = new ArrayList<User>();

    }

    public static Hall getInstance() {
        return new Hall();
    }

    public ArrayList<String> getRooms() {
        return rooms;
    }


    public ArrayList<String> getAvailableReservations(Context con ,String roomname, String date) {

        try {
            Room room = deserializeXMLToRoomObject(con,roomname);
            
            ArrayList<String> availabletimes = new ArrayList<String>();
            for(int i = openinghour; i == closinghour;i++) {
                String time = String.format("%02d.00",i);
                if (!room.isReserved(time,date))
                    availabletimes.add(time);
            }
            return availabletimes;


        } catch (FileNotFoundException e) {
            // Tiedostoa ei ole -> palautetaan kaikki ajat
            return(new ArrayList<String>(Arrays.asList("12.00","13.00")));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    public void makeReservation(Context con,String time, String roomname, String date) {
        Room room = null;
        try {
            room = this.deserializeXMLToRoomObject(con,roomname);

        } catch (FileNotFoundException e) {
            room = new Room(roomname);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Serialisaatio","Ei lue oikein");

        }
        room.addReservation(date,time);

        try {
            this.serializeRoomObjectToXML(con, room);

        } catch (NullPointerException e) {
            Log.e("Serialisaatio","nul pointer");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private Room deserializeXMLToRoomObject(Context con, String roomname) throws Exception {
        String filename = roomname.replaceAll(" ","") + fname;

        InputStream is = null;
        is = con.openFileInput(filename);
        Serializer ser = new Persister();
        Room room = ser.read(Room.class,is);
        Log.i("Test",room.getName());
        is.close();
        return room;
    }

    private void serializeRoomObjectToXML(Context con, Room room) throws Exception {
        String filename = room.getName().replaceAll(" ","") + fname;
        OutputStream os = con.openFileOutput(filename,Context.MODE_PRIVATE);
        Serializer ser = new Persister();
        ser.write(room,os);
        os.close();

    }

    public void addUser(String un, String pw) {
        User user = new User(un, pw);
        users.add(user);
    }
}
