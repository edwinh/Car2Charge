package com.example.edwin.car2charge;

import java.util.Locale;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
    private static final String[] FROM = {CarDatabase.C_ADDRESS, CarDatabase.C_BATTERY, CarDatabase.C_DISTANCE, CarDatabase.C_DISTANCE_CP};
    private static final int[] TO = {R.id.text_address, R.id.text_load, R.id.text_distance, R.id.text_distance_cp};
    private final static String TAG = "MainActivity";
    private Intent carDownloadIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.app_name);

        if (!isNetworkConnected()){
            Toast.makeText(getApplicationContext(), "Sorry, no internet connection", Toast.LENGTH_LONG).show();
        }

        else {
            //Toast.makeText(getApplicationContext(), "Yes! network", Toast.LENGTH_LONG).show();
            getContentResolver().delete(CarDataProvider.CONTENT_URI, null, null);
            String[] projection = {CarDatabase.C_ID, CarDatabase.C_ADDRESS, CarDatabase.C_LICENSE, CarDatabase.C_BATTERY, CarDatabase.C_DISTANCE, CarDatabase.C_DISTANCE_CP};
            Cursor cars = getContentResolver().query(CarDataProvider.CONTENT_URI, projection, null, null, null);

            SimpleCursorAdapter adapter = new SimpleCursorAdapter (this, R.layout.row, cars, FROM, TO);
            adapter.setViewBinder(VIEW_BINDER);
            setListAdapter(adapter);
            carDownloadIntent = new Intent(getApplicationContext(), CarDownloaderService.class);

            if (savedInstanceState == null) {
                carDownloadIntent.setData(Uri.parse(getURLincludingLocation()));
                startService(carDownloadIntent);
            } else {
                Log.i(TAG, "savedInstance is not null");
            }
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }

    static final ViewBinder VIEW_BINDER = new ViewBinder() {

        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

            if (view.getId() == R.id.text_load) {
                int load = cursor.getInt(cursor.getColumnIndex(CarDatabase.C_BATTERY));
                String strLoad = String.format("Load: %s%%", load);
                ((TextView)view).setText(strLoad);
                return true;
            }

            if (view.getId() == R.id.text_distance) {
                int distance = cursor.getInt(cursor.getColumnIndex(CarDatabase.C_DISTANCE));
                String strDistance = String.format("Distance: %sm", distance);
                ((TextView)view).setText(strDistance);
                return true;
            }

            if (view.getId() == R.id.text_distance_cp) {
                int distanceCP = cursor.getInt(cursor.getColumnIndex(CarDatabase.C_DISTANCE_CP));
                String strDistanceCP = String.format("Distance to CP: %sm", distanceCP);
                ((TextView)view).setText(strDistanceCP);
                return true;
            }
            return false;
        }
    };

    private int refreshMenuId;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        MenuItem refresh = menu.findItem(R.id.refresh_option_item);
        refresh.setIntent(carDownloadIntent);
        refreshMenuId = refresh.getItemId();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == refreshMenuId) {
            item.getIntent().setData(Uri.parse(getURLincludingLocation()));
            startService(item.getIntent());
        }
        if (item.getItemId() == R.id.menu_settings) {
            startActivity(new Intent(this, PrefsActivity.class));
        }

        return true;
    }

    private String getURLincludingLocation() {
        String url = "";
        final String WS_URL = "http://%s:%d/c2c/ws.php?lat=%f&lng=%f&perc=%d";
        String server = ((C2cApplication)getApplication()).prefs.getString("server", "192.168.72.104");
        int perc = Integer.parseInt(((C2cApplication)getApplication()).prefs.getString("max_load", "30"));
        // Default lat/long DamSquare in Amsterdam
        double lat = 52.372789;
        double lng = 4.893669;

        GPSTracker gps = new GPSTracker(this);
        if (gps.canGetLocation()) {
            lat = gps.getLatitude(); if (lat == 0) { lat = 52.372789; }
            lng = gps.getLongitude(); if (lng == 0) { lng = 4.893669; }
            //Toast.makeText(getApplicationContext(), "Your location is -\nLat: " + lat + "\nLong: " + lng, Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(getApplicationContext(), "Cannot get location, returning Damsquare", Toast.LENGTH_SHORT).show();
        }

        url = String.format(Locale.getDefault(), WS_URL, server, 8081, lat, lng, perc);
        Log.d("GetURL", url);
        return url;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        String[] projection = {CarDatabase.C_LAT, CarDatabase.C_LONG, CarDatabase.C_ADDRESS, CarDatabase.C_LICENSE, CarDatabase.C_BATTERY};
        Cursor cursor = getContentResolver().query(Uri.withAppendedPath(CarDataProvider.CONTENT_URI,
                String.valueOf(id)), projection, null, null,null);
        cursor.moveToFirst();
        double lat = cursor.getDouble(0);
        double lng = cursor.getDouble(1);

        Intent i = new Intent(getApplicationContext(), DetailActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        i.putExtra("lat", lat);
        i.putExtra("long", lng);
        i.putExtra("address", cursor.getString(2));
        i.putExtra("license", cursor.getString(3));
        i.putExtra("battery", cursor.getString(4));
        startActivity(i);
        cursor.close();

//		//super.onListItemClick(l, v, position, id);
    }
}
