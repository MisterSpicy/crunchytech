package com.crunchytech.breeze.server;
import android.util.Log;
import static com.crunchytech.breeze.Constants.TAG;

public class UserInfo {
	public String name;
	public String ident;
	public String profileurl;
	public String picurl;
	public String headline;
	
	
	public UserInfo(String name, String ident, String purl, String headline, String picurl){
		Log.i(TAG, "Creating UserInfo");
		this.name = name;
		this.ident = ident;
		this.profileurl = purl;
		this.picurl = picurl;
		this.headline = headline;
	}
}
