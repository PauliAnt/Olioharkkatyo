package com.example.olioharkkaty;

import android.util.Log;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Reservation {

    private String[] listofsports = {"Tennis", "Badminton", "Squash"};
    private int[] sportImageResources = {R.drawable.ic_tennisball,R.drawable.ic_badminton,R.drawable.ic_squashball};

    @Attribute
    private int id;

    @Element
    private String date;

    public String getDate() {return date;}

    @Element
    private String time;

    public String getTime() {return time;}

    @Element
    private String username;

    @Element
    private String describtion;

    @Element
    private int sportid;

    @Element
    private String room;

    public String getRoom() { return room; }

    public Reservation(String d, String t, String un, String des, int si , int i, String r){
        date = d;
        time = t;
        username = un;
        // Väliaikainen arvo helpottaa xml käsittelyssä
        if (des == null || des.length()==0) {
            des = "_empty_";
        }
        describtion = des;
        sportid = si;
        id = i;
        room = r;
    }

    public int getSportImageResource(){ return sportImageResources[sportid]; }
    // Tyhjä rakentaja Simple kirjastoa varten
    private Reservation(){}


}
