package com.example.olioharkkaty;

public class User {

    private String u_name;
    private String password;
    private String f_name = "";
    private String l_name = "";
    private String adress = "";

    public User(String un, String pw){
        u_name = un;
        password = pw;
    }

    public void setU_name(String un) {
        u_name = un;
    }

    public void setPassword(String pw){
        password = pw;
    }

    public void setAdress(String ad) {
        adress = ad;
    }

    public void setF_name(String fn){
        f_name = fn;
    }

    public void setL_name(String ln){
        l_name = ln;
    }


    public String getU_name(){
        return u_name;
    }

    public String getPassword(){
        return password;
    }

    public String getF_name(){
        return f_name;
    }

    public String getL_name(){
        return l_name;
    }

    public String getAdress(){
        return adress;
    }
}


