package com.crunchytech.breeze;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class LinkedInProfileViewer extends Activity {

	public LinkedInProfileViewer() {
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		String publicProfileUrl = intent.getStringExtra("profileurl");
		WebView webview = new WebView(this);
		setContentView(webview);
		webview.loadUrl(publicProfileUrl);
	}

}
