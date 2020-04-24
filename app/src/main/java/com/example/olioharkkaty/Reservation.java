package com.example.olioharkkaty;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Reservation {

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

    public Reservation(String d, String t, String un, String des, String s){
        date = d;
        time = t;
        username = un;
        describtion = des;
        sport = s;
    }

    // Tyhjä rakentaja Simple kirjastoa varten
    private Reservation(){}


}
