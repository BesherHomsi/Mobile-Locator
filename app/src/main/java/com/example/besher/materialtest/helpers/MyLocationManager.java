package com.example.besher.materialtest.helpers;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.example.besher.materialtest.models.MyLocation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Besher on 03/06/18.
 */

public class MyLocationManager {

    private static final String TAG = "Cell";
    private LocationManager locationManager;
    private LocationListener locationListener;


    public MyLocation getLocation(Context context) {

        MyLocation location = null;
        location = getLocationViaGps(context);
        if (location == null)
            location = getLocationViaTower(context);

        return location;
    }

    public MyLocation getLocationViaGps(Context context) {

        MyLocation location = null;


        return location;
    }


    public MyLocation getLocationViaTower(Context context) {

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        String providerName = LocationManager.NETWORK_PROVIDER;
        Location location = locationManager.getLastKnownLocation(providerName);
        updateWithLocation(location);

        //Cell-ID and WiFi location updates.
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                Log.d("TAG", "locationListner");
                updateWithLocation(location);
                String Text = "My current location is: " + "Latitude = " + location.getLatitude() + "Longitude = " + location.getLongitude();
                Log.d("TAG", "Starting..");
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("TAG", "onSatatus");
            }

            public void onProviderEnabled(String provider) {
                Log.d("TAG", "onProviderEnable");
            }

            public void onProviderDisabled(String provider) {
                Log.d("TAG", "onProviderDisble");
            }
        };
    }

    private void updateWithLocation(Location location) {
        Log.d("TAG", "UpdateWithLocation");
        String displayString;
        if (location != null) {
            Double lat = location.getLatitude();
            Double lng = location.getLongitude();
            Long calTime = location.getTime();
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(calTime);
            String time = formatter.format(calendar.getTime()).toString();
            displayString = "The Lat: " + lat.toString() + " \nAnd Long: " + lng.toString() + "\nThe time of Calculation: " + time;
        } else {
            displayString = "No details Found";
        }
    }


}
