package com.example.besher.materialtest.models;

/**
 * Created by Besher on 03/06/18.
 */

public class MyCLocation {

    public static int TOWER_LOCATION = 0;
    public static int GPS_LOCATION = 1;

    public int type;
    private double lng, lat;



    public MyCLocation(int type, double lat, double lng) {
        this.type = type;
        this.lng = lng;
        this.lat = lat;
    }

    public int getType() {
        return type;
    }

    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setType(int type) {
        this.type = type;
    }
}
