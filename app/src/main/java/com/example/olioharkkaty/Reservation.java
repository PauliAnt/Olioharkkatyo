package com.example.olioharkkaty;

import android.util.Log;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Reservation implements Comparable<Reservation>{
    // Class handles reservation information
    private String[] listofsports = {"Tennis", "Badminton", "Squash"};
    private int[] sportImageResources = {R.drawable.ic_tennisball,R.drawable.ic_badminton,R.drawable.ic_squashball};

    @Attribute
    protected int id;
    public int getId() { return id; }

    @Element(required = false)
    private String date;
    public String getDate() {return date;}

    @Element
    protected String time;
    public String getTime() {return time;}
    public void setTime(String time) {this.time = time;}

    @Element
    protected String username;

    @Element
    protected String describtion;

    public String getDescribtion() {
        if (describtion.equals("_empty_"))
            return "";
        return describtion;
    }

    public void setDescribtion(String describtion) {
       if (describtion.length()==0)
           this.describtion = "_empty_";
       else
           this.describtion = describtion;
    }

    @Element
    protected int sportid;
    public int getSportid() { return sportid; }
    public void setSportid(int sportid) { this.sportid = sportid; }

    @Element
    protected String room;

    public String getRoom() { return room; }

    public Reservation(String d, String t, String un, String des, int si , int i, String r){
        date = d;
        time = t;
        username = un;
        // Temporary value helps with xml handling
        if (des == null || des.length()==0) {
            des = "_empty_";
        }
        describtion = des;
        sportid = si;
        id = i;
        room = r;
    }

    public int getSportImageResource(){ return sportImageResources[sportid]; }

    public String getSport() {return listofsports[sportid];}


    @Override
    public int compareTo(Reservation reservation) {
        // Implemented method for sorting reservations
        if(this.date.equals(reservation.date))
            return(this.time.compareTo(reservation.time));
        else
            return this.date.compareTo(reservation.date);
    }

    // Empty builder for SimpleXML and subclass
    protected Reservation(){}


}
@Root
class RegularReservation extends Reservation{
    @Element
    private int weekday;
    public int getWeekday() { return weekday; }

    public RegularReservation(String t, int wd, String un, String des, int si, int i, String r) {
        super();
        time = t;
        weekday = wd;
        username = un;
        describtion = des;
        sportid = si;
        id = i;
        room = r;
    }

    private RegularReservation(){}
}
