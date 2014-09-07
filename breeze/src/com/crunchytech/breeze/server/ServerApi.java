package com.crunchytech.breeze.server;

import static com.crunchytech.breeze.Constants.TAG;

import java.util.HashMap;
import java.util.Map;

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

	public ServerApi() {
		Log.i(TAG, "Instantiate Server Api?");
	}

	public static void sendRegistration(final String name, final String id, final String purl, final String headline) {
		Log.i(TAG, "Send Registration to the server");
		String requestUrl = serverURL + register;
		Log.i(TAG, "requestURL = " + requestUrl);

		// Tag used to cancel the request
		String tag_json_obj = "json_obj_req";

		// ProgressDialog pDialog = new ProgressDialog(this);
		// pDialog.setMessage("Loading...");
		// pDialog.show();

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
				params.put("purl", purl);
				params.put("headline", headline);

				return params;
			}
		};
		Breeze.getInstance().addToRequestQueue(postRequest); 
	}

	public static void getNearby() {
		Log.i(TAG, "Get All Nearby Users");
		String requestUrl = serverURL + getnearby;
		
		
		// prepare the Request
		JsonObjectRequest getRequest = new JsonObjectRequest(Method.GET, requestUrl, null,
		    new Response.Listener<JSONObject>() 
		    {
		        @Override
		        public void onResponse(JSONObject response) {   
		                        // display response     
		            Log.d(TAG, response.toString());
		        }
		    }, 
		    new Response.ErrorListener() 
		    {
		         @Override
		         public void onErrorResponse(VolleyError error) {            
		            Log.d(TAG, error.toString());
		       }
		    }
		);
		 
		// add it to the RequestQueue   
		Breeze.getInstance().addToRequestQueue(getRequest); 
	}
}
