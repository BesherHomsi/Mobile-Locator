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
    /*
    public static String PREF_NAME = "SilenceItem";

    public static String KEY_ITEMS_LIST = "silence_list";

    public static String KEY_ID = "id";
    public static String KEY_TITLE = "title";
    public static String KEY_SELECTED_DAYS = "selected_days";
    public static String KEY_START_DATE = "start_date";
    public static String KEY_END_DATE = "end_date";
    public static String KEY_START_HOUR = "start_hour";
    public static String KEY_START_MIN = "start_min";
    public static String KEY_START_AM_PM = "start_am_pm";
    public static String KEY_END_HOUR = "end_hour";
    public static String KEY_END_MIN = "end_min";
    public static String KEY_END_AM_PM = "end_am_pm";*/


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

    public SilenceItem(String id, String name, String days, long startDate, long endDate,
                       int startHour, int startMin, String start_AM_PM, int endHour, int endMin,
                       String end_AM_PM,ArrayList<String> alarmsIDs, String status) {
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

    public void setAlarmIDs(ArrayList<String> alarmIDs) {
        this.alarmIDs = alarmIDs;
    }

    /*

        public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }

    private static JSONObject itemToJsonObject(SilenceItem silenceItem) {
        JSONObject object = new JSONObject();
        try {
            object.put(KEY_ID, silenceItem.getId());
            object.put(KEY_TITLE, silenceItem.getTitle());
            object.put(KEY_SELECTED_DAYS, silenceItem.getSelectedDays());
            object.put(KEY_START_DATE, String.valueOf(silenceItem.getStartDate()));
            object.put(KEY_END_DATE, String.valueOf(silenceItem.getEndDate()));
            object.put(KEY_START_HOUR, String.valueOf(silenceItem.getStartHour()));
            object.put(KEY_START_MIN, String.valueOf(silenceItem.getStartMin()));
            object.put(KEY_START_AM_PM, silenceItem.getStart_AM_PM());
            object.put(KEY_END_HOUR, silenceItem.getEndHour());
            object.put(KEY_END_MIN, silenceItem.getEndMin());
            object.put(KEY_END_AM_PM, silenceItem.getEnd_AM_PM());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    private static SilenceItem jsonObjectToItem(JSONObject object) {
        SilenceItem silenceItem = null;
        String id = "";
        String title = "";
        String selectedDays = "";
        long startDate = 0;
        long endDate = 0;
        int startHour = 0;
        int startMin = 0;
        String start_am_pm = "";
        int endHour = 0;
        int endMin = 0;
        String end_am_pm= "";

        try {
            id = object.getString(KEY_ID);
        } catch (JSONException e) {
        }
        try {
            title = object.getString(KEY_TITLE);
        } catch (JSONException e) {
        }
        try {
            selectedDays = object.getString(KEY_SELECTED_DAYS);
        } catch (JSONException e) {
        }
        try {
            startDate = object.getLong(KEY_START_DATE);

        } catch (JSONException e) {
        }
        try {
            endDate = object.getLong(KEY_END_DATE);
        } catch (JSONException e) {
        }
        try {
            startHour = object.getInt(KEY_START_HOUR);
        } catch (JSONException e) {
        }
        try {
            startMin = object.getInt(KEY_START_MIN);
        } catch (JSONException e) {
        }
        try {
            start_am_pm = object.getString(KEY_START_AM_PM);
        } catch (JSONException e) {
        }
        try {
            endHour = object.getInt(KEY_END_HOUR);
        } catch (JSONException e) {
        }
        try {
            endMin = object.getInt(KEY_END_MIN);
        } catch (JSONException e) {
        }
        try {
            end_am_pm = object.getString(KEY_END_AM_PM);
        } catch (JSONException e) {
        }

        return new SilenceItem(id,title, selectedDays, startDate, endDate, startHour, startMin,
                start_am_pm, endHour, endMin, end_am_pm);
    }

    public static void saveListToPref(Context context, ArrayList<SilenceItem> list) {
        JSONArray itemList = new JSONArray();
        JSONObject prefJson = new JSONObject();

        for (int i = 0; i < list.size(); i++) {
            itemList.put(itemToJsonObject(list.get(i)));
        }

        try {
            prefJson.put(KEY_ITEMS_LIST, itemList.toString());

            SharedPreferences prefs = context.getSharedPreferences(
                    PREF_NAME, Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(KEY_ITEMS_LIST, itemList.toString());
            editor.apply();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<SilenceItem> getListFromPref(Context context) {
        ArrayList<SilenceItem> list = new ArrayList<>();

        SharedPreferences prefs = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);

        String prefListString = prefs.getString(KEY_ITEMS_LIST, "");

        if (!prefListString.isEmpty()) {
            try {
                JSONArray prefList = new JSONArray(prefListString);
                for (int i = 0; i < prefList.length(); i++) {
                    list.add(jsonObjectToItem(prefList.getJSONObject(i)));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public static boolean updateListPref(Context context, SilenceItem silenceItem) {

        SharedPreferences prefs = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);

        String prefListString = prefs.getString(KEY_ITEMS_LIST, "");

        if (!prefListString.isEmpty()) {
            try {
                JSONArray prefList = new JSONArray(prefListString);
                for (int i = 0; i < prefList.length(); i++) {
                    if (jsonObjectToItem(prefList.getJSONObject(i)).getId().equals(silenceItem.getId())) {
                        prefList.put(i, itemToJsonObject(silenceItem));
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(KEY_ITEMS_LIST, prefList.toString());
                        editor.apply();
                        return true;
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static boolean deleteListPref(Context context, SilenceItem silenceItem) {

        SharedPreferences prefs = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);

        String prefListString = prefs.getString(KEY_ITEMS_LIST, "");

        if (!prefListString.isEmpty()) {
            try {
                JSONArray prefList = new JSONArray(prefListString);
                for (int i = 0; i < prefList.length(); i++) {
                    if (jsonObjectToItem(prefList.getJSONObject(i)).getId().equals(silenceItem.getId())) {
                        prefList.remove(i);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(KEY_ITEMS_LIST, prefList.toString());
                        editor.apply();
                        return true;
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
*/
}
