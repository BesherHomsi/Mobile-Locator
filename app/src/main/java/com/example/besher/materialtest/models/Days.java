package com.example.besher.materialtest.models;

import android.content.Context;

import com.example.besher.materialtest.R;

import java.util.HashMap;

/**
 * Created by Besher on 02/24/18.
 */

public class Days {
    public final static String Monday = "mon";
    public final static String Tuesday = "tus";
    public final static String Wednesday = "wed";
    public final static String Thursday = "thu";
    public final static String Friday = "fri";
    public final static String Saturday = "sat";
    public final static String Sunday = "sun";

   public static HashMap<Integer, String> getDays() {
        HashMap<Integer, String> days = new HashMap<>();
        days.put(0, Monday);
        days.put(1, Tuesday);
        days.put(2, Wednesday);
        days.put(3, Thursday);
        days.put(4, Friday);
        days.put(5, Saturday);
        days.put(6, Sunday);
        return days;
    }

}
