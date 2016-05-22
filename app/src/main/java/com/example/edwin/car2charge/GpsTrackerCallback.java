package com.example.edwin.car2charge;

import android.location.Location;

/**
 * Created by edwin on 21-5-2016.
 */
public interface GpsTrackerCallback {
    void LocationFound (Location location);
    void LocationNotAvailable();
}
