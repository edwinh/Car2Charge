package com.example.edwin.car2charge;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class C2cApplication extends Application {
	private static final String TAG = C2cApplication.class.getSimpleName();
	SharedPreferences prefs;
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "Application started");	
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
	}
}
