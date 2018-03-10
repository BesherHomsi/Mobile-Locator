package com.example.besher.materialtest.helpers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;

import com.example.besher.materialtest.models.Days;
import com.example.besher.materialtest.models.MyCLocation;
import com.example.besher.materialtest.models.SilenceItem;
import com.example.besher.materialtest.receiver.SilenceTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Besher on 02/23/18.
 */

public class SilenceManager {

    public final static String PREF_NAME = "SilenceItem";
    public final static String KEY_ITEMS_LIST = "silence_list";
    public final static String KEY_LAST_ALARM_ID = "last_alarm_id";

    //public final static String KEY_ITEM_ID = "item_id";
    public final static String KEY_ID = "id";
    public final static String KEY_TITLE = "title";
    public final static String KEY_SELECTED_DAYS = "selected_days";
    //public final static String KEY_DAYS_SELECTED_ENTRIES = "days_selected";
    public final static String KEY_START_DATE = "start_date";
    public final static String KEY_END_DATE = "end_date";
    public final static String KEY_START_HOUR = "start_hour";
    public final static String KEY_START_MIN = "start_min";
    public final static String KEY_START_AM_PM = "start_am_pm";
    public final static String KEY_END_HOUR = "end_hour";
    public final static String KEY_END_MIN = "end_min";
    public final static String KEY_END_AM_PM = "end_am_pm";
    public final static String KEY_STATUS = "status";
    public final static String KEY_ALARM_IDS = "alarm_ids";
    public final static String KEY_LNG = "lng";
    public final static String KEY_LAT = "lat";
    public final static String KEY_LOCATION_STATUS = "location_status";


    public final static String ACTION_SET_SILENCE = "set_silence";
    public final static String ACTION_REMOVE_SILENCE = "remove_silence";

    //public final static String PREF_NAME_PENDING = "pendingItem";
    //public final static String KEY_PENDING_ITEM = "pending_item";
    //public final static String KEY_PENDING_ID = "PENDING_id";


    public static void scheduleAlarm(Context context, SilenceItem silenceItem, int dayOfWeek) {


//        Calendar cur_cal = new GregorianCalendar();
//        cur_cal.setTimeInMillis(System.currentTimeMillis());//set the current time and date for this calendar
//
//        Calendar cal = new GregorianCalendar();
//        cal.add(Calendar.DAY_OF_YEAR, cur_cal.get(Calendar.DAY_OF_YEAR));
//        cal.set(Calendar.HOUR_OF_DAY, 21);
//        cal.set(Calendar.MINUTE, 40);
//        cal.set(Calendar.SECOND, cur_cal.get(Calendar.SECOND));
//        cal.set(Calendar.MILLISECOND, cur_cal.get(Calendar.MILLISECOND));
//        cal.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
//        cal.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));


        Calendar startDate = Calendar.getInstance();
        Calendar myCalender = Calendar.getInstance();


        startDate.setTimeInMillis(silenceItem.getStartDate());
        Calendar temp = Calendar.getInstance();

        myCalender.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        myCalender.set(Calendar.HOUR_OF_DAY, silenceItem.getStartHour());
        myCalender.set(Calendar.MINUTE, silenceItem.getStartMin());

        temp.setTimeInMillis(System.currentTimeMillis());

        if (myCalender.getTimeInMillis() < temp.getTimeInMillis()) {
            myCalender.add(Calendar.DAY_OF_YEAR, new GregorianCalendar().get(Calendar.DAY_OF_WEEK) - 1);
        }
/*
        myCalender.set(
                myCalender.get(startDate.YEAR),
                myCalender.get(startDate.MONTH),
                myCalender.get(startDate.DAY_OF_MONTH),
                silenceItem.getStartHour(),
                silenceItem.getStartMin()
        );*/
        int id = getNewID(context);
        silenceItem.addNewAlarmID(String.valueOf(id));
        //set
        Bundle bundle = new Bundle();
        bundle.putString("item", silenceItem.getLocationStatus() + ":" + silenceItem.getLat() + ":" + silenceItem.getLng());
        Intent intent = new Intent(context, SilenceTask.class);
        intent.setAction(ACTION_SET_SILENCE);
        intent.putExtras(bundle);
        PendingIntent pIntent = PendingIntent.getBroadcast(context,
                id, intent, 0);

        AlarmManager alarm = (AlarmManager) context
                .getSystemService(context.ALARM_SERVICE);

        alarm.setRepeating(AlarmManager.RTC_WAKEUP, myCalender.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pIntent);


        //remove
        Calendar removeCalender = Calendar.getInstance();
/*
        removeCalender.set(
                removeCalender.get(startDate.YEAR),
                removeCalender.get(startDate.MONTH),
                removeCalender.get(startDate.DAY_OF_MONTH),
                silenceItem.getEndHour(),
                silenceItem.getEndMin()
        );
*/
        removeCalender.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        removeCalender.set(Calendar.HOUR_OF_DAY, silenceItem.getEndHour());
        removeCalender.set(Calendar.MINUTE, silenceItem.getEndMin());

        if (removeCalender.getTimeInMillis() < System.currentTimeMillis()) {
            removeCalender.add(Calendar.DAY_OF_YEAR, new GregorianCalendar().get(Calendar.DAY_OF_WEEK) - 1);
        }
        Bundle bundle1 = new Bundle();
        bundle.putString("item", silenceItem.getLocationStatus() + ":" + silenceItem.getLat() + ":" + silenceItem.getLng());
        id = getNewID(context);
        silenceItem.addNewAlarmID(String.valueOf(id));
        Intent intentRemove = new Intent(context, SilenceTask.class);
        intentRemove.setAction(ACTION_REMOVE_SILENCE);
        intentRemove.putExtras(bundle1);
        PendingIntent pIntentRemove = PendingIntent.getBroadcast(context,
                id, intentRemove, 0);

        AlarmManager alarmRemove = (AlarmManager) context
                .getSystemService(context.ALARM_SERVICE);

        alarmRemove.setRepeating(AlarmManager.RTC_WAKEUP, removeCalender.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pIntentRemove);
    }

    public static void cancelAlarm(Context context, SilenceItem silenceItem) {
        String s = ACTION_SET_SILENCE;
        for (String alarmId :
                silenceItem.getAlarmIDs()) {
            Intent myIntent = new Intent(context, SilenceTask.class);
            myIntent.setAction(s);
            try {
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                        Integer.valueOf(alarmId), myIntent, 0);
                pendingIntent.cancel();

                AlarmManager alarmManager = (AlarmManager) context
                        .getSystemService(context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);

            } catch (NumberFormatException e) {
            }
            if (s.equals(ACTION_SET_SILENCE))
                s = ACTION_REMOVE_SILENCE;
            else
                s = ACTION_SET_SILENCE;
        }

        silenceItem.setStatus("off");
        updateListPref(context, silenceItem);
    }

    public static void registerEvent(Context context, SilenceItem silenceItem) {
        String[] days = silenceItem.getSelectedDays().split(" ");
        for (int i = 0; i < days.length; i++) {
            switch (days[i]) {
                case Days.Monday:
                    scheduleAlarm(context, silenceItem, Calendar.MONDAY);
                    break;
                case Days.Tuesday:
                    scheduleAlarm(context, silenceItem, Calendar.TUESDAY);
                    break;
                case Days.Wednesday:
                    scheduleAlarm(context, silenceItem, Calendar.WEDNESDAY);
                    break;
                case Days.Thursday:
                    scheduleAlarm(context, silenceItem, Calendar.THURSDAY);
                    break;
                case Days.Friday:
                    scheduleAlarm(context, silenceItem, Calendar.FRIDAY);
                    break;
                case Days.Saturday:
                    scheduleAlarm(context, silenceItem, Calendar.SATURDAY);
                    break;
                case Days.Sunday:
                    scheduleAlarm(context, silenceItem, Calendar.SUNDAY);
                    break;
                default:
                    break;
            }
        }
        silenceItem.setStatus("on");
        updateListPref(context, silenceItem);
    }

    public static boolean checkIfEventISActiveWithLocation(Context context) {
        Calendar now = Calendar.getInstance();
        int currentHour = now.get(Calendar.HOUR_OF_DAY);
        ArrayList<SilenceItem> events = SilenceManager.getListFromPref(context);
        MyCLocation myCLocation = MyLocationManager.getLocationViaTower(context);
        boolean found = false;
        if (myCLocation != null) {
            for (int i = 0; i < events.size(); i++) {
                SilenceItem event = events.get(i);
                if (event.getLocationStatus().equals("on") && event.isSet() &&
                        (event.getStartHour() <= currentHour) && (event.getEndHour() >= currentHour)) {
                    float[] distance = new float[2];
                    Location.distanceBetween(myCLocation.getLat(), myCLocation.getLng(), events.get(i).getLat(), events.get(i).getLng(), distance);
                    if (distance[0] <= Constant.RADUIES) {
                        found = true;
                        break;
                    }
                }
            }
        }

        return found;

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
            object.put(KEY_ALARM_IDS, String.valueOf(silenceItem.alarmIdToString()));
            object.put(KEY_STATUS, silenceItem.getStatus());
            object.put(KEY_LNG, String.valueOf(silenceItem.getLng()));
            object.put(KEY_LAT, String.valueOf(silenceItem.getLat()));
            object.put(KEY_LOCATION_STATUS, silenceItem.getLocationStatus());
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
        String end_am_pm = "";
        ArrayList<String> alarmIDS = new ArrayList<>();
        String status = "";
        Double lng = 0.0;
        Double lat = 0.0;
        String locationStatus = "";


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
        try {
            String[] alarmIDs = object.getString(KEY_ALARM_IDS).split(",");
            for (String alarmId :
                    alarmIDs) {
                alarmIDS.add(alarmId);
            }
        } catch (JSONException e) {
        }
        try {
            status = object.getString(KEY_STATUS);
        } catch (JSONException e) {
        }

        try {
            lng = object.getDouble(KEY_LNG);
        } catch (JSONException e) {
        }

        try {
            lat = object.getDouble(KEY_LAT);
        } catch (JSONException e) {
        }
        try {
            locationStatus = object.getString(KEY_LOCATION_STATUS);
        } catch (JSONException e) {
        }
        return new SilenceItem(id, title, selectedDays, startDate, endDate, startHour, startMin,
                start_am_pm, endHour, endMin, end_am_pm, alarmIDS, status, lat, lng, locationStatus);
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

    public static int getNewID(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);

        int lastID = prefs.getInt(KEY_LAST_ALARM_ID, 0);
        lastID++;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_LAST_ALARM_ID, lastID);
        editor.apply();


        return lastID;
    }
}
