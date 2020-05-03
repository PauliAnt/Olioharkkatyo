package com.example.olioharkkaty;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserManager {

    private User currentuser;
    private String una;
    private User admin;

    private UserManager() {}


    public static final UserManager instance = new UserManager();
    public static UserManager getInstance() {return instance;}


    public boolean checkLogin(Context con, String username, String password) {
        if (username.equals(admin.getUserName())&&password.equals(admin.getPassword())){
            currentuser=admin;
            return true;
        }
        SharedPreferences mPrefs = con.getSharedPreferences("Users", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(username, "defValue");

        if (json.equals("defValue"))
            return false;
        else {
            User user = gson.fromJson(json, User.class);
            if (password.equals(user.getPassword())) {
                currentuser = user;
                return true;
            } else
                return false;
        }
    }

    public void updateInfo(Context con, String un, String pw, String fn, String ln, String ad){
        currentuser.setUserName(un);
        currentuser.setPassword(pw);
        currentuser.setFirstName(fn);
        currentuser.setLastName(ln);
        currentuser.setAddress(ad);
        writeToFile(con, currentuser);
    }

    public void addReservationid(Context con, int id){
        currentuser.addReservation(id);
        writeToFile(con, currentuser);
    }

    public User getCurrentUser() {return currentuser;}

    public String getCurrentUserName() {return currentuser.getUserName();}

    public void addUsers(User user) {


    }

    public boolean checkPassword(String pw){
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(pw);
        boolean b = m.find();
        if (b==false)
            return false;
        for (char c : pw.toCharArray()){
            if (Character.isDigit(c))
                return true;
        }
        return false;
    }

    private void writeToFile(Context con, User user){
        SharedPreferences mPrefs = con.getSharedPreferences("Users", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        una = user.getUserName();
        prefsEditor.putString(una, json);
        prefsEditor.commit();
    }

    public void setCurrentUser(User user){
        currentuser = user;
    }

    public void addUser(Context con, String un, String pw) {
        User user = new User(un, pw);
        this.writeToFile(con, user);
        currentuser = user;
    }

    public void setAdmin(String[] adminsignin){
       admin = new Admin(adminsignin[0],adminsignin[1]);
    }

    public Boolean checkAdmin(){
        if (currentuser instanceof Admin)
            return true;
        return false;
    }

}
