package com.example.besher.materialtest.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.besher.materialtest.ControllerApplication;
import com.example.besher.materialtest.models.Logs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Besher on 03/16/18.
 */

public class LogManager {


    public final static String PREF_NAME = "log_pref";
    public final static String KEY_LOGS_LIST = "logs_list";

    public final static String KEY_EVENT = "event";
    public final static String KEY_LOG_TIME = "time";



    public static void logAction(String action){
        Calendar now = Calendar.getInstance();
        ArrayList<Logs> logList = new ArrayList<>();

        //load old logs list
        logList = getListFromPref(ControllerApplication.getInstance());
        //add new log
        Logs log = new Logs(action, now.getTimeInMillis());
        logList.add(log);
        //save logs
        saveListToPref(ControllerApplication.getInstance(), logList);
    }

    private static JSONObject itemToJsonObject(Logs log) {
        JSONObject object = new JSONObject();
        try {
            object.put(KEY_EVENT, log.getLogName());
            object.put(KEY_LOG_TIME, String.valueOf(log.getLogtime()));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    private static Logs jsonObjectToItem(JSONObject object) {

        String logName = "";
        long logTime = 0;

        try {
            logName = object.getString(KEY_EVENT);
        } catch (JSONException e) {
        }
        try {
            logTime = object.getLong(KEY_LOG_TIME);
        } catch (JSONException e) {
        }

        return new Logs(logName,logTime);
    }

    public static void saveListToPref(Context context, ArrayList<Logs> list) {
        JSONArray itemList = new JSONArray();
        JSONObject prefJson = new JSONObject();

        for (int i = 0; i < list.size(); i++) {
            itemList.put(itemToJsonObject(list.get(i)));
        }

        try {
            prefJson.put(KEY_LOGS_LIST, itemList.toString());

            SharedPreferences prefs = context.getSharedPreferences(
                    PREF_NAME, Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(KEY_LOGS_LIST, itemList.toString());
            editor.apply();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Logs> getListFromPref(Context context) {
        ArrayList<Logs> list = new ArrayList<>();

        SharedPreferences prefs = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);

        String prefListString = prefs.getString(KEY_LOGS_LIST, "");

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

}
