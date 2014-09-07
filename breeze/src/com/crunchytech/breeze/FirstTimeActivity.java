package com.crunchytech.breeze;

import com.crunchytech.breeze.server.ServerApi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class FirstTimeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();

		setContentView(R.layout.activity_first_time);

		final Activity thisActivity = this;
		
		Button loginBtn = (Button)findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
    			Intent intent = new Intent(thisActivity, LinkedInLogin.class);
    		    startActivityForResult(intent, 0);
            }
        });
		ServerApi.updateNearbyUsers();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		ServerApi.updateNearbyUsers();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	
		System.out.println("On result code " + resultCode);

		switch (resultCode) {
	  	case RESULT_OK: {
	  		System.out.print("Checking..");
			System.out.print("User login " + Breeze.getProfile().isLogin());

	  		if(Breeze.getProfile().isLogin()) {
				finish();
			}
	  	}
	  	break;
	  }
	}

}
