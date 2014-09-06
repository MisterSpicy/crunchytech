package com.example.bluetoothtest;

import static com.example.bluetoothtest.Constants.TAG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

public class Datastore {

    public static ArrayList<Friend> userList = new ArrayList<Friend>();
    public static Map<String, Friend> idMap = new HashMap<String, Friend>();
    
    public static Map<String, Integer> userCount = new HashMap<String, Integer>();
    public static Map<String, Long> lastSeen = new HashMap<String, Long>();

    /**
     * Putting a new user into the user store
     */
    public static void addConnection(String name, String id) {
        Log.i(TAG, "addConnection: id= " + id + " name= " + name);

        Friend f = new Friend(name, id);

        if (idMap.get(f.id) != null) {
            Log.i(TAG, "addConnection : friend ID already exists in the map");
        }
        else{
            Log.i(TAG, "addConnection: friend ID does NOT exist in the map ... add it!");
            idMap.put(id, f);
            userList.add(f);
        }
    }

    /**
     * addToIdMap
     * 
     * @param id
     * @param f
     */
    public static void addToIdMap(String id, Friend f) {
        Log.i(TAG, "addToIdMap id: " + id + " Friend name: " + f.name);
        idMap.put(id, f);
    }

    /**
     * getAllConnections - return the map of all connections
     * 
     * @return
     */
    public static ArrayList<Friend> getAllConnections() {
        Log.i(TAG, "getAllConnections...");

        for (Friend f : userList) {
            Log.i(TAG, "Friend name: " + f.name + " Friend ID: " + f.id);
        }

        return userList;
    }

    public static Long getCurrentTime() {
        Log.i(TAG, "Current system time = " + System.currentTimeMillis() % 1000);
        return System.currentTimeMillis() % 1000;
    }

    public static long setLastSeen(String name) {
        Log.i(TAG, "setLastSeen: setting name=" + name + " last seen");

        return 0;
    }
}
