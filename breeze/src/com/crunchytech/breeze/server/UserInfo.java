package com.crunchytech.breeze.server;
import android.util.Log;
import static com.crunchytech.breeze.Constants.TAG;

public class UserInfo {
	public String name;
	public String ident;
	public String purl;
	
	
	public UserInfo(String name, String ident, String purl){
		Log.i(TAG, "Creating UserInfo");
		this.name = name;
		this.ident = ident;
		this.purl = purl;
	}
}
