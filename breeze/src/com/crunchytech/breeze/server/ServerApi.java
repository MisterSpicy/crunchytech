package com.crunchytech.breeze.server;
import static com.crunchytech.breeze.Constants.TAG;
import android.util.Log;

public class ServerApi {
	static String serverURL = "http://107.170.245.92:5000/";
	static String register = "register";
	static String getnearby = "getnearby";
	
	
	public ServerApi(){
		Log.i(TAG, "Instantiate Server Api?");
	}
	
	public static void sendRegistration(String name, String id, String purl){
		Log.i(TAG, "Send Registration to the server");
		String requestUrl = serverURL+register;
		Log.i(TAG, "requestURL = " + requestUrl);
		
		
	}
	
	public static void getNearby(){
		Log.i(TAG, "Get All Nearby Users");
		String requestUrl = serverURL+getnearby;
	}
}
