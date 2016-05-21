package com.example.edwin.car2charge;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.widget.Toast;

public class DetailActivity extends android.support.v4.app.FragmentActivity {
	private GoogleMap map;
	private LatLng latLong;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		int status=GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
		if (status!=ConnectionResult.SUCCESS)
			Toast.makeText(getApplicationContext(), "No Google Play", Toast.LENGTH_LONG).show();
				
		setContentView(R.layout.detailactivity);
		map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		
		setTitle(R.string.detail);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			double lat = extras.getDouble("lat");
			double lng = extras.getDouble("long");
			String license = extras.getString("license");
			String address = extras.getString("address");
			String battery = extras.getString("battery");
			
			//Toast.makeText(this, "lat: " + lat + " long: " + lng, Toast.LENGTH_LONG).show();
			latLong = new LatLng(lat, lng);
			CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(latLong)
				.zoom(16f)
				.build();
		
			CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
			
			map.animateCamera(cameraUpdate);
			Marker car = map.addMarker(new MarkerOptions()
			.position(latLong)
			.title(license + " (" + battery + "%)")
			.snippet(address)
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car)));
			car.showInfoWindow();
			
			map.setMyLocationEnabled(true);	
			
		}
	}
	
}
