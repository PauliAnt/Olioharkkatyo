package com.example.olioharkkaty;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserManager {

    private User currentuser;
    private String una;
    private User admin;
    private Context con;

    private UserManager() {}
    public static final UserManager instance = new UserManager();
    public static UserManager getInstance() {return instance;}

    public void setContext(Context con){this.con = con;}

    public boolean checkLogin(String username, String password) {
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
            String pw = hashPassword(password, user.getSalt());
            if (pw.equals(user.getPassword())) {
                currentuser = user;
                return true;
            } else
                return false;
        }
    }

    public void updateInfo(String un, String fn, String ln, String ad){
        currentuser.setUserName(un);
        currentuser.setFirstName(fn);
        currentuser.setLastName(ln);
        currentuser.setAddress(ad);
        writeToFile(currentuser);
    }

    public void addReservationid(int id){
        currentuser.addReservation(id);
        writeToFile(currentuser);
    }

    public User getCurrentUser() {return currentuser;}

    public String getCurrentUserName() {return currentuser.getUserName();}

    public String hashPassword(String password, String salt){
        String hashedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }

    public String generateSalt(){
        int lLimit = 97;
        int rLimit = 122;
        int length = 5;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomInt = lLimit + (int)
                    (random.nextFloat() * (rLimit - lLimit + 1));
            buffer.append((char) randomInt);
        }
        String salt = buffer.toString();
        return salt;
    }


    public boolean checkPassword(String pw){
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(pw);
        boolean bool = m.find();
        if (bool==false)
            return false;
        for (char c : pw.toCharArray()){
            if (Character.isDigit(c))
                return true;
        }
        return false;
    }

    private void writeToFile(User user){
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

    public void addUser(String un, String pw) {
        String sal = generateSalt();
        String pw1 = hashPassword(pw, sal);
        User user = new User(un, pw1);
        user.setSalt(sal);
        writeToFile(user);
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

    public void removeReservation(Reservation reservation){
        currentuser.removeReservationId(reservation.getId());
        writeToFile(currentuser);
    }

}
