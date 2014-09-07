package com.crunchytech.breeze;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class LinkedInProfile {
	
	static LinkedInProfile s_linkedInService;
	public static LinkedInProfile getInstance() {
		if(s_linkedInService == null) { 
			s_linkedInService = new LinkedInProfile();
		}
		return s_linkedInService;
	}
	
	private String TAG = "BreezeeLinkedIn";
	private class Profile {
		String id = "";
		String firstName = "";
		String lastName = "";
		String headline = "";
		String accessToken = "";
		String profileUrl = "";
		String photoUrl = "";
		
		public void setId(String id) {
			profile.id = id;
		}
		
		public String getId() { 
			return profile.id;
		}
		
		public String getProfileUrl() {
			return profile.profileUrl;
		}

		public void setProfileUrl(String profileUrl) {
			profile.profileUrl = profileUrl;
		}
		
		public void setPhotoUrl(String photoUrl) {
			profile.photoUrl = photoUrl;
		}
		
		public String getPhotoUrl() {
			return profile.photoUrl;
		}

		public void setFirstName(String first) {
			profile.firstName = first;
		}
		
		public void setLastName(String last) {
			profile.lastName = last;
		}
		
		public void setHeadline(String headline_) {
			profile.headline = headline_;
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
	}
	
	public Profile profile;
	private RequestQueue queue;
	
	//Constructor
	public LinkedInProfile () {
		profile = new Profile();
		queue = VolleySingleton.getInstance().getRequestQueue();		
		
		loadProfile();
	}
	
	public boolean isLogin() {
		return profile.accessToken != null;
	}
	
	public void loginDidSucceed(String token) {
		profile.accessToken = token;
		
		SharedPreferences sharedPref =  PreferenceManager.getDefaultSharedPreferences(Breeze.getAppContext());
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString("saved_authtoken", token);
		editor.commit();

		Log.i(TAG, "Login succeeded profile token = " + profile.accessToken);
		
		if(token != null) {
			getProfileInfo();
		}
	}
	
	public void logout() {
		Log.i(TAG, "LOGOUT");
		loginDidSucceed(null);
	}
	
	public void loadProfile() {
		SharedPreferences sharedPref =  PreferenceManager.getDefaultSharedPreferences(Breeze.getAppContext());
		profile.accessToken = sharedPref.getString("saved_authtoken", null);	
		Log.i(TAG, "Loaded profile token = " + profile.accessToken);
		
		if(profile.accessToken != null) {
			getProfileInfo();			
		}
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
	
	public String getAuthTokenURL(String code) {
		String requestURL = "https://www.linkedin.com/uas/oauth2/accessToken?grant_type=authorization_code" + 
                                           "&code=" + code + 
                                           "&redirect_uri=" + REDIRECT_URI + 
                                           "&client_id=" + API_KEY + 
                                           "&client_secret=" + SECRET_KEY;
	    return requestURL;
	}
	
	public String getProfileInfo() {
	    // Create a new HttpClient and Post Header
		String host = "https://api.linkedin.com/v1/people/~:(id,first-name,last-name,headline,picture-url,public-profile-url)?format=json&oauth2_access_token=" + getAccessToken();

		JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, host, null,
			    new Response.Listener<JSONObject>() 
			    {
			        @Override
			        public void onResponse(JSONObject response) {   
			                        // display response     
			            Log.d("Response", response.toString());
			            try {
							profile.setFirstName(response.getString("firstName"));
				            profile.setLastName(response.getString("lastName"));
				            profile.setHeadline(response.getString("headline"));
				            profile.setProfileUrl(response.getString("publicProfileUrl"));
				            profile.setPhotoUrl(response.getString("pictureUrl"));
				            profile.setId(response.getString("id"));
				            // QL: notify somebody about this.
				            
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
			    }, 
			    new Response.ErrorListener() 
			    {
					@Override
					public void onErrorResponse(VolleyError arg0) {
			            Log.e("Response", arg0.toString());						
					}
			    }
			);
			 
			// add it to the RequestQueue   
			queue.add(getRequest);		

			return "default";
	}
	

	private final String SCOPE = "";
	private final String API_KEY = "75g5lmhj1mfxi9";
	private final String SECRET_KEY = "S4zXc6kD9lE8shDU";
	private final String STATE = "BREEZYFOSHEEZEE";
	public static final String REDIRECT_URI = "https://breeze.ninja";
}
