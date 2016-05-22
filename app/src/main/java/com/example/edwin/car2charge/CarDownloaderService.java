package com.example.edwin.car2charge;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

public class CarDownloaderService extends Service {
	private static final String DEBUG_TAG = "CarDownloaderService";
	private DownloaderTask carDownloader;
	
	public int onStartCommand(Intent intent, int flags, int startId)  {
		URL carPath;
		
		if (intent == null) return Service.START_FLAG_REDELIVERY;
		
		try {
			carPath = new URL(intent.getDataString());
			carDownloader = new DownloaderTask();
			carDownloader.execute(carPath);
		} catch (MalformedURLException e) {
			Log.e(DEBUG_TAG, "Bad URL", e);
		}
		return Service.START_FLAG_REDELIVERY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private class DownloaderTask extends AsyncTask<URL, Void, Boolean> {


        @Override
		protected Boolean doInBackground(URL... params) {
			boolean succeeded = false;
			URL downloadPath = params[0];
			if (downloadPath != null) {
                String json = getCarData(downloadPath);
                if (json.length() != 0) {
                    succeeded = insertJsonData(json);
                }
			}
			return succeeded;
		}

		public String getCarData(URL url) {
			
			try {
               URLConnection connection = url.openConnection();
               InputStream inputStream = connection.getInputStream();
               BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

               StringBuilder result = new StringBuilder();
               String line;

               while ( (line = reader.readLine()) != null ){
                   result.append(line);
               }
               return result.toString();
           } catch (Exception e) {
               e.printStackTrace();
           }
            return null;
    	}
		
		public boolean insertJsonData(String json) {
			boolean succeeded = false;
			String DEBUG_TAG =  "CarDownloaderService$DownloaderTask";
			getContentResolver().delete(CarDataProvider.CONTENT_URI, null, null);
			
			try {
				JSONArray array = new JSONArray(json);
				for (int i=0; i<=array.length()-1; i++) {
					
					ContentValues values = new ContentValues();
					values.put(CarDatabase.C_LICENSE, array.getJSONObject(i).getString("license"));
					values.put(CarDatabase.C_ADDRESS, array.getJSONObject(i).getString("full_address"));
					values.put(CarDatabase.C_LAT, array.getJSONObject(i).getDouble("lat"));
					values.put(CarDatabase.C_LONG, array.getJSONObject(i).getDouble("long"));
					values.put(CarDatabase.C_BATTERY, array.getJSONObject(i).getString("load").substring(0, 2));
					values.put(CarDatabase.C_CHARGING, 0);
					values.put(CarDatabase.C_DISTANCE, array.getJSONObject(i).getDouble("distance"));
					values.put(CarDatabase.C_DISTANCE_CP, array.getJSONObject(i).getDouble("cp_distance"));
					getContentResolver().insert(CarDataProvider.CONTENT_URI, values);
				}
				Log.d(DEBUG_TAG, Integer.toString(array.length()));
                succeeded = true;
							
			} catch (JSONException e) {
				e.printStackTrace();
                succeeded = false;
			}

			return succeeded;
		}
	}

}
