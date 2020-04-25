package com.example.olioharkkaty;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class UserManager {
    // todo kaikki user toiminta tämän luokan kautta!!!

    private User currentuser;

    // korvataan myöhemmin tiedoston lukemisella
    public ArrayList<User> users;

    private UserManager() {
        users = new ArrayList<User>();
    }

    public UserManager getInstance() {return new UserManager();};

    public boolean checkLogin(String username, String password) {
        // todo lisää toiminnallisuus, tsekkaa käyttäjänimen ja salasanan user listasta, palauttaa true jos löytyy, false jos ei
        return true;
    }

    public String getCurrentUserName() {return currentuser.getUserName();}
}
