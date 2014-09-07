package com.crunchytech.breeze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class LinkedInProfile {
	private String TAG = "BreezeeLinkedIn";
	private class Profile {
		String firstName = "";
		String lastName = "";
		String headline = "";
		String accessToken = "";
	}
	
	public Profile profile;
	
	//Constructor
	public LinkedInProfile () {
		profile = new Profile();
	}
	
	public void setFirstName(String first) {
		profile.firstName = first;
	}
	
	public void setLastName(String last) {
		profile.lastName = last;
	}
	
	public void setHeadline(String headline) {
		profile.headline = headline;
	}
	
	public void setAccessToken(String token) {
		profile.accessToken = token;
	}
	
	public String getFirstName() {
		
		return profile.firstName;
	}
	public String getLastName() {
		
		return profile.lastName;
	}
	
	public String getHeadline() {
		return profile.headline;
	}
	
	public void parseResult(String result) {
		
	}
	
	public String getAccessToken() {
		if (profile.accessToken != "") {
			return profile.accessToken;
		} else {
			Log.d(TAG, "ERROR: NO ACCESS TOKEN");
			return "";
		}
	}
	
	public String getLinkedInAuthURL() {
		String requestURL = "https://www.linkedin.com/uas/oauth2/authorization?response_type=code" + 
                                           "&client_id=" + API_KEY + 
                                           "&scope=" + "" + SCOPE + 
                                           "&state=" + STATE + 
                                           "&redirect_uri=" + REDIRECT_URI;
		return requestURL;
	}
	
	public String getAuthToken(String code) {
		String requestURL = "https://www.linkedin.com/uas/oauth2/accessToken?grant_type=authorization_code" + 
                                           "&code=AUTHORIZATION_CODE" + code + 
                                           "&redirect_uri=" + REDIRECT_URI + 
                                           "&client_id=YOUR_API_KEY" + API_KEY + 
                                           "&client_secret=" + SECRET_KEY;
	    return requestURL;
	}
	
	public String getProfileInfo(String access_token) {
	    // Create a new HttpClient and Post Header
		String host = "api.linkedin.com/v1/people/~?oauth2_access_token=" + getAccessToken();
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpGet httpget = new HttpGet("https://" + host);

	    HttpResponse response;
	    
	    try {
	        response = httpclient.execute(httpget);
	        // Examine the response status
	        Log.d("TAG",response.getStatusLine().toString());

	        // Get hold of the response entity
	        HttpEntity entity = response.getEntity();
	        // If the response does not enclose an entity, there is no need
	        // to worry about connection release

	        if (entity != null) {
	            InputStream instream = entity.getContent();
	            String result = convertStreamToString(instream);
	            instream.close();
	            Log.d(TAG, "HTTP GET Response: " + result);
	            parseResult(result);
	        }


	    } catch (Exception e) {}
		return "default";
	}
	
	private static String convertStreamToString(InputStream is) {

	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}
	
	private final String SCOPE = "";
	private final String API_KEY = "75g5lmhj1mfxi9";
	private final String SECRET_KEY = "S4zXc6kD9lE8shDU";
	private final String STATE = "BREEZYFOSHEEZEE";
	public static final String REDIRECT_URI = "https://breeze.ninja";
}
