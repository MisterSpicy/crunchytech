package com.crunchytech.breeze;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.crunchytech.breeze.server.ServerApi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LinkedInLogin extends Activity {
	private final String TAG = "LinkedInLogin";
	private static String linkedInCode = "";
	private static String linkedInToken = "";
    RequestQueue queue;
	
	public LinkedInLogin() {
		queue = VolleySingleton.getInstance().getRequestQueue();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WebView webview = new WebView(this);
		webview.setWebViewClient(new WebViewClient()  {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Log.d(TAG, "shouldOverride: " + url);
				try {
					URL fullUrl = new URL(url);
					String responseCode = "";
					Map<String, String> query_pairs = new LinkedHashMap<String, String>();
					Log.d(TAG, "Query: " + fullUrl.getQuery());
					String[] pairs = fullUrl.getQuery().split("&");
				  for (String pair : pairs) {
					    final int idx = pair.indexOf("=");
					    query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), 
					    			URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
				  }
				  
				  responseCode = query_pairs.get("code");
				  Log.d(TAG, "Code: " + responseCode);
				  if (responseCode != null) {
					  linkedInCode = responseCode;
					  String accessURL = Breeze.getProfile().getAuthTokenURL(linkedInCode);
					  Log.d(TAG, "accessUrl: " + accessURL);
					  JsonObjectRequest jsObjRequest = new JsonObjectRequest
						        (Request.Method.POST, Breeze.getProfile().getAuthTokenURL(linkedInCode), 
						        		 null, new Response.Listener<JSONObject>() {

						    @Override
						    public void onResponse(JSONObject response) {
						    	try {
									linkedInToken = response.getString("access_token");
									Breeze.getProfile().loginDidSucceed(linkedInToken);
									String name = Breeze.getProfile().profile.getFirstName() + " " + Breeze.getProfile().profile.getLastName();
									String id = Breeze.getProfile().profile.getId();
									String headline = Breeze.getProfile().profile.getHeadline();
									String profileurl = Breeze.getProfile().profile.getProfileUrl();
									String picurl = Breeze.getProfile().profile.getPhotoUrl();
									ServerApi.sendRegistration(name, id, profileurl, headline, picurl);
									
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								Log.d(TAG, "LinkedInToken: " + linkedInToken);
								
							    Intent intent = new Intent();

								if (getParent() == null) {
								    setResult(Activity.RESULT_OK);
								} else {
								    getParent().setResult(Activity.RESULT_OK);
								}
								finish();

						    }
						    
						}, new Response.ErrorListener() {

						    @Override
						    public void onErrorResponse(VolleyError error) {
						        // TODO Auto-generated method stub

						    }
						});

					  queue.add(jsObjRequest);
					  					  
				  }
				  return true;
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			}
			
		});
		setContentView(webview);
		webview.loadUrl(Breeze.getProfile().getLinkedInAuthURL());
	}
}
