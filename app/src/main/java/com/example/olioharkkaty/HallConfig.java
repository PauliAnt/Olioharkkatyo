package com.example.olioharkkaty;

import android.content.Context;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;

@Root
public class HallConfig {
    // Class is used to save configuration settings from file to object

    @ElementArray
    private String[] admin; // [username, password]
    public String[] getAdmin() { return admin; }

    @ElementArray
    private int[][] openhours;
    public int[][] getOpenHours(){return openhours;}

    @ElementArray
    private String[] sports;
    public String[] getSports(){return sports;}

    @ElementList
    private ArrayList<RoomInfo> rooms;
    @Root
    static class RoomInfo{
        @Element
        private int id;
        @Element
        private String name;

        private RoomInfo(String name,int id){
            this.id = id;
            this.name = name;
        }

        // Empty builder for SimpleXML library
        private RoomInfo(){}


    }

    public HashMap<Integer, String> getRoomMap(){
        HashMap hm = new HashMap<Integer, String>();
        for (RoomInfo room:rooms) {
            hm.put(room.id, room.name);
        }
        return hm;
    }
    public void addRoom(String roomname, int id){
        rooms.add(new RoomInfo(roomname,id));
    }
    public void deleteRoom(String roomname) {
        ListIterator<RoomInfo> itr = rooms.listIterator();
        while(itr.hasNext()){
            if(itr.next().name.equals(roomname)) {
                itr.remove();
                break;
            }
        }
    }

    // Empty builder for SimpleXML library
    private HallConfig(){}
}
