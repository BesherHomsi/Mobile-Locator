package com.example.besher.materialtest.models;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Besher on 02/04/18.
 */

public class SilenceItem implements Serializable {


    public static String OFF = "off";
    public static String ON = "on";

    private int itemId;
    private String id;
    private String title;
    private String selectedDays;
    private long startDate;
    private long endDate;
    private int startHour;
    private int startMin;
    private int endHour;
    private int endMin;
    private String start_AM_PM;
    private String end_AM_PM;
    private String status;
    private ArrayList<String> alarmIDs = new ArrayList<>();
    private Double lng;
    private Double lat;
    private String locationStatus;


    public SilenceItem(String id, String name, String days, long startDate, long endDate,
                       int startHour, int startMin, String start_AM_PM, int endHour, int endMin,
                       String end_AM_PM,ArrayList<String> alarmsIDs, String status, double lat, double lng,
                       String locationStatus) {
        this.id = id;
        this.title = name;
        this.selectedDays = days;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startHour = startHour;
        this.startMin = startMin;
        this.start_AM_PM = start_AM_PM;
        this.endHour = endHour;
        this.endMin = endMin;
        this.end_AM_PM = end_AM_PM;
        this.alarmIDs = alarmsIDs;
        this.status = status;
        this.lat = lat;
        this.lng = lng;
        this.locationStatus = locationStatus;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSelectedDays(String selectedDays) {
        this.selectedDays = selectedDays;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }

    public void setStart_AM_PM(String start_AM_PM) {
        this.start_AM_PM = start_AM_PM;
    }

    public void setEnd_AM_PM(String end_AM_PM) {
        this.end_AM_PM = end_AM_PM;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public void setEndMin(int endMin) {
        this.endMin = endMin;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMin() {
        return startMin;
    }

    public String getStart_AM_PM() {
        return start_AM_PM;
    }

    public String getEnd_AM_PM() {
        return end_AM_PM;
    }

    public String getSelectedDays() {
        return selectedDays;
    }

    public long getStartDate() {
        return startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMin() {
        return endMin;
    }

    public String alarmIdToString(){
        String text ="";
        for (String id:
             this.alarmIDs) {
            text = text+ "," +id ;
        }
      return text;
    }

    public void addNewAlarmID(String id){
        this.alarmIDs.add(id);
    }

    public ArrayList<String> getAlarmIDs() {
        return alarmIDs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSet() {
        if(this.status.equals(OFF))
            return false;
        else
            return true;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getLocationStatus() {
        return locationStatus;
    }

    public void setLocationStatus(String locationStatus) {
        this.locationStatus = locationStatus;
    }

    public void setAlarmIDs(ArrayList<String> alarmIDs) {
        this.alarmIDs = alarmIDs;
    }

}
