package com.example.edwin.car2charge;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

public class PrefsActivity extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (Build.VERSION.SDK_INT >= 11) {
			getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefsFragment()).commit();
		} else {
			addPreferencesFromResource(R.xml.prefs);
		}
		
	}
	
	@SuppressLint("NewApi")
	public static class PrefsFragment extends PreferenceFragment {

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.prefs);
		}
		
	}

}


