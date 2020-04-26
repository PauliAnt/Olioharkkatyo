package com.example.olioharkkaty;
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
        users.add(user);
    }

    public void setCurrentUser(User user){
        currentuser = user;
    }

    public void addUser(String un, String pw){
        User user = new User(un, pw);
        addUsers(user);
        setCurrentUser(user);
    }
}
