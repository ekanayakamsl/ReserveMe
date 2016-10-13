package com.smartlogic.saranga.reserveme;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by sranga on 5/28/2016.
 */
public class MyLocationListner implements LocationListener {

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.e("Lat :",""+ location.getLatitude());
            Log.e("Lat :",""+location.getLatitude());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}
