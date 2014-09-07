package com.crunchytech.breeze.server;

import static com.crunchytech.breeze.Constants.TAG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.crunchytech.breeze.Breeze;

public class ServerApi {
	static String serverURL = "http://107.170.245.92:5000/";
	static String register = "register";
	static String getnearby = "getnearby";
	
	public static ArrayList<UserInfo> nearbyUsers = new ArrayList<UserInfo>();
	

	public ServerApi() {
		Log.i(TAG, "Instantiate Server Api?");
	}
	
	public static void updateNearbyUsers(){
		getNearby();
	}

	/**
	 * JAVA DOC DICKSHIT
	 * 
	 * @param name
	 * @param id
	 * @param profileurl
	 * @param headline
	 */
	public static void sendRegistration(final String name, final String id,
			final String profileurl, final String headline, final String picurl) {
		Log.i(TAG, "Send Registration to the server");
		String requestUrl = serverURL + register;
		Log.i(TAG, "requestURL = " + requestUrl);

		// Tag used to cancel the request
		String tag_json_obj = "json_obj_req";


		StringRequest postRequest = new StringRequest(Method.POST, requestUrl,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d(TAG, response);

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						// pDialog.hide();
					}
				}) {

			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				params.put("name", name);
				params.put("ident", id);
				params.put("profileurl", profileurl);
				params.put("headline", headline);
				params.put("picurl", picurl);

				return params;
			}
		};
		Breeze.getInstance().addToRequestQueue(postRequest);
	}
	

	private static ArrayList<UserInfo> getNearby() {
		Log.i(TAG, "Get All Nearby Users");
		String requestUrl = serverURL + getnearby;
		ArrayList<UserInfo> userinfos = new ArrayList<UserInfo>();
		
		// prepare the Request
		JsonObjectRequest getRequest = new JsonObjectRequest(Method.GET,
				requestUrl, null, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						// display response
						Log.d(TAG, response.toString());
						nearbyUsers = parseRequest(response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d(TAG, error.toString());
					}
				});

		// add it to the RequestQueue
		Breeze.getInstance().addToRequestQueue(getRequest);
		
		return null;
	}

	public static ArrayList<UserInfo> parseRequest(JSONObject obj) {
		Log.i(TAG, "PARSE JSON RESPONSE");
		ArrayList<UserInfo> userinfos = new ArrayList<UserInfo>();

		try {
			JSONArray arr = obj.getJSONArray("users");
			int length = arr.length();
			Log.i(TAG, "JSONLength = " + length);

			for (int j = 0; j < length; j++) {
				JSONObject anobj = arr.getJSONObject(j);
				userinfos
						.add(new UserInfo(anobj.getString("name"), anobj
								.getString("ident"), anobj
								.getString("profileurl"), anobj
								.getString("headline"), anobj
								.getString("picurl")));
			}
			
//			callback.onResponse(userinfos);
		} catch (JSONException e) {
			Log.e(TAG, "I fucked up the JSON object parsing");
			e.printStackTrace();
		}
		return userinfos;
	}
}

/*
 * EXAMPLE OF JSON RETURNED { "users": [ { "headline": "I AM A BANANA", "ident":
 * "12345", "name": "bob", "picurl": "knife.jpg", "profileurl":
 * "httpcolonslash4" }, { "headline": "I AM A BANANA", "ident": "12346", "name":
 * "tom", "picurl": "gun.jpg", "profileurl": "httpcolonslash2" }, { "headline":
 * "I AM A BANANA", "ident": "12347", "name": "larry", "picurl": "needle.jpg",
 * "profileurl": "httpcolonslash3" } ] }
 */
