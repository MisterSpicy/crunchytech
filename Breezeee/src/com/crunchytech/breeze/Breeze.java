package com.crunchytech.breeze;

import android.app.Application;
import android.content.Context;

public class Breeze extends Application {
	
	private static Breeze mInstance;
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        
        this.setAppContext(getApplicationContext());
    }

    public static Breeze getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    public void setAppContext(Context mAppContext) {
        this.mAppContext = mAppContext;
    }
}