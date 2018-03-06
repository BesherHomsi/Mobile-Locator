package com.example.besher.materialtest.models;

/**
 * Created by Asus on 3/6/2018.
 */

public class MyLocation {
    public static int TOWER_LOCATION = 0;
    public static int GPS_LOCATION = 1;

    public int type;
    private float lang, lat;


    public MyLocation(int type, float lang, float lat) {
        this.type = type;
        this.lang = lang;
        this.lat = lat;
    }

    public int getType() {
        return type;
    }

    public float getLang() {
        return lang;
    }

    public float getLat() {
        return lat;
    }
}
