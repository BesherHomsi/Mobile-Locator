package com.example.besher.materialtest.helpers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import com.example.besher.materialtest.models.MyCLocation;


/**
 * Created by Besher on 03/06/18.
 */

public class MyLocationManager {


    private static final String TAG = "Cell";

    public static boolean getLocationViaGps(Context context, LocationListener locationListener) {
        //Cell-ID and WiFi location updates.
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static MyCLocation getLocationViaTower(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        ;
        String providerName = LocationManager.NETWORK_PROVIDER;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        Location location = locationManager.getLastKnownLocation(providerName);
        if(location!=null){
            return new MyCLocation(MyCLocation.TOWER_LOCATION, location.getLatitude(), location.getLongitude());
        }else {
            return null;
        }
    }


}
