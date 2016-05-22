package com.example.edwin.car2charge;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

public class GpsTracker implements LocationListener {
    private final static String TAG = "GpsTracker";
    private final Context mContext;
    private GpsTrackerCallback callback;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 100; // 100 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public GpsTracker(Context context, GpsTrackerCallback callback) {
        this.mContext = context;
        this.callback = callback;
        //getLocation();
    }

    public Location getLocation() {
        try {
            Criteria criteria;
            criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);

            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            String provider = locationManager.getBestProvider(criteria, true);
            Log.d(TAG, "Provider: " + provider);

            locationManager.requestLocationUpdates(provider, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

            // Check if location services are available
            if (provider.equals(LocationManager.PASSIVE_PROVIDER))
            {
                canGetLocation = false;
                callback.LocationNotAvailable();
                return null;
            }
            this.canGetLocation = true;

            location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                locationManager.removeUpdates(this);
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                callback.LocationFound(location);
            }
         } catch (Exception e) {
            e.printStackTrace();
        }
         return location;
    }
 
    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        return latitude;
    }
 
    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        return longitude;
    }
 
    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }
 
    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
 
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");
 
        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
 
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });
 
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
    }
 
    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            locationManager.removeUpdates(this);
            callback.LocationFound(location);
        }
    }
 
    @Override
    public void onProviderDisabled(String provider) {
        getLocation();
    }
 
    @Override
    public void onProviderEnabled(String provider) {
        getLocation();
    }
 
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

	

}
