package com.example.olioharkkaty;

import android.util.Log;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.HashMap;

@Root
public class HallConfig {

    @ElementList
    private ArrayList<RoomInfo> rooms;
    public HashMap<String, Integer> getRoomMap(){
        HashMap<String, Integer> hm = new HashMap<String, Integer>();
        for (RoomInfo room:rooms){
            hm.put(room.name, room.id);
        }
        return hm;
    }

    @ElementArray
    private String[] sports;
    public String[] getSports(){return sports;}

    @ElementArray
    private int[][] openhours;
    public int[][] getOpenHours(){return openhours;}

    /*
    public HallConfig(int[] ids, String[] names, int[][] openhours,String[] sports) {
        rooms = new ArrayList<RoomInfo>();
        for (int i = 0; i < ids.length; i++)
            rooms.add(new RoomInfo(ids[i], names[i]));
        this.openhours = openhours;
        this.sports = sports;
    }*/
    private HallConfig(){}

    @Root
    static class RoomInfo{
        @Element
        private int id;
        @Element
        private String name;


        private RoomInfo(){}


    }
}
