package com.example.olioharkkaty;

import android.util.Log;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Reservation {

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
    private String sport;

    public Reservation(String d, String t, String un, String des, String s,int i){
        date = d;
        time = t;
        username = un;
        // Väliaikainen arvo helpottaa xml käsittelyssä
        if (des == null || des.length()==0) {
            des = "_empty_";
        }
        describtion = des;
        sport = s;
        id = i;
    }

    // Tyhjä rakentaja Simple kirjastoa varten
    private Reservation(){}


}
