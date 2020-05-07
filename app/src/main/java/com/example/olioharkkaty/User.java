package com.example.olioharkkaty;
import android.os.UserManager;

import java.util.ArrayList;

public class User {

    // User class maintains users information, password and salt used to hash password
    // Also admin user extends user class, but doesn't have info else than username and password

    private String u_name; // this is username
    private String password;
    private String f_name = ""; // this is first name
    private String l_name = ""; // this is last name
    private String adress = "";
    private String salt = "";
    private ArrayList<Integer> reservationids;

    public User(String un, String pw){
        reservationids = new ArrayList<Integer>();
        u_name = un;
        password = pw;
    }

    public void setUserName(String un) {
        u_name = un;
    }

    public void setPassword(String pw){
        password = pw;
    }

    public void setAddress(String ad) {
        adress = ad;
    }

    public void setFirstName(String fn){ f_name = fn; }

    public void setLastName(String ln){
        l_name = ln;
    }

    public void setSalt(String sl) {salt = sl; }

    public void addReservation(int id){reservationids.add(id);}


    public String getUserName(){
        return u_name;
    }

    public String getPassword(){
        return password;
    }

    public String getFirstName(){
        return f_name;
    }

    public String getLastName(){
        return l_name;
    }

    public String getAddress(){return adress;}

    public String getSalt(){return salt;}

    public ArrayList<Integer> getReservations(){return reservationids;}

    public void removeReservationId(int id) {
        reservationids.remove(reservationids.indexOf(id));
    }

}

class Admin extends User {
    public Admin(String un, String pw) {
        super(un, pw);
    }
}

