package com.example.olioharkkaty;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Reservation implements Comparable<Reservation>{
    // Class handles reservation information

    private int comparevalue = 1;
    private String[] listofsports = {"Tennis", "Badminton", "Squash"};
    private int[] sportImageResources = {R.drawable.ic_tennisball,R.drawable.ic_badminton,R.drawable.ic_squash_ball};

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
        // todo sorttaus kuntoo
        if(getClass() == reservation.getClass())
            return this.subCompare(reservation);
        return 0;
    }


    protected int subCompare(Reservation reservation){
        if (this.date.equals(reservation.date))
            return (this.time.compareTo(reservation.time));
        else
            return this.date.compareTo(reservation.date);
    }
    // Empty builder for SimpleXML and subclass
    protected Reservation(){}


}
@Root
class RegularReservation extends Reservation{
    private int comparevalue = 10;

    @Element
    private int weekday;
    public int getWeekday() { return weekday; }

    @Element
    private String firstdate;
    public String getFirstdate() { return firstdate; }

    public RegularReservation(String t, int wd, String un, String des, int si, int i, String r, String fd) {
        super();
        time = t;
        weekday = wd;
        username = un;
        if (des == null || des.length()==0) {
            des = "_empty_";
        }
        describtion = des;
        sportid = si;
        id = i;
        room = r;
        firstdate = fd;
    }

    @Override
    protected int subCompare(Reservation reservation) {
        if (this.weekday == ((RegularReservation)reservation).weekday)
            return this.time.compareTo(reservation.time);
        else
            return Integer.compare(this.weekday,((RegularReservation) reservation).getWeekday());
    }


    // Empty builder for SimpleXML
    private RegularReservation(){}
}
