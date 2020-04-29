package com.example.olioharkkaty;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;

public class UserManager {
    // todo kaikki user toiminta tämän luokan kautta!!!

    private User currentuser;

    // korvataan myöhemmin tiedoston lukemisella
    public ArrayList<User> users;

    private UserManager() {
        users = new ArrayList<User>();
        users.add(new User("Tommi", "Teemuki"));
    }


    public static final UserManager instance = new UserManager();
    public static UserManager getInstance() {return instance;}

    public boolean checkLogin(String username, String password) {
        // todo lisää toiminnallisuus, tsekkaa käyttäjänimen ja salasanan user listasta, palauttaa true jos löytyy, false jos ei
        return true;
    }

    public void updateInfo(String un, String pw, String fn, String ln, String ad){
        currentuser.setUserName(un);
        currentuser.setPassword(pw);
        currentuser.setFirstName(fn);
        currentuser.setLastName(ln);
        currentuser.setAddress(ad);
    }

    public User findUser(String un){
        for (User user:users){
            if (user.getUserName().equals(un))
                    return user;
        }
        return null;
    }

    public User getCurrentUser() {return currentuser;}

    public String getCurrentUserName() {return currentuser.getUserName();}

    public void addUsers(User user) {


    }

    public void setCurrentUser(User user){
        currentuser = user;
    }

    public void addUser(Context con, String un, String pw) {
        SharedPreferences mPrefs = con.getSharedPreferences("Users", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        User user = new User(un, pw);
        this.setCurrentUser(user);
        Gson gson = new Gson();
        String json = gson.toJson(user);
        String una = user.getUserName();
        prefsEditor.putString(una, json);
        prefsEditor.commit();
    }

}
