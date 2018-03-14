package com.example.besher.materialtest.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.besher.materialtest.models.MyContact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Besher on 03/10/18.
 */

public class MyContactManager {

    public final static String PREF_NAME = "my_contacts";

    public final static String KEY_ID = "id";
    public final static String KEY_NAME = "name";
    public final static String KEY_NUMBER = "number";
    public final static String KEY_STATUS = "status";




    private static JSONObject itemToJsonObject(MyContact myContact) {
        JSONObject object = new JSONObject();
        try {
            object.put(KEY_ID, myContact.getId());
            object.put(KEY_NAME, myContact.getName());
            object.put(KEY_NUMBER, myContact.getNumber());
            object.put(KEY_STATUS, myContact.getStatus());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    private static MyContact jsonObjectToItem(JSONObject object) {

        String id = "";
        String name = "";
        String number = "";
        String status = "";

        try {
            id = object.getString(KEY_ID);
        } catch (JSONException e) {
        }
        try {
            name = object.getString(KEY_NAME);
        } catch (JSONException e) {
        }
        try {
            number = object.getString(KEY_NUMBER);
        } catch (JSONException e) {
        }
        try {
            status = object.getString(KEY_STATUS);
        } catch (JSONException e) {
        }

        return new MyContact(id, name, number, status);
    }

    public static void saveListToPref(Context context, ArrayList<MyContact> list,
                                      String KEY_CONTACTS_LIST) {
        JSONArray itemList = new JSONArray();
        JSONObject prefJson = new JSONObject();

        for (int i = 0; i < list.size(); i++) {
            itemList.put(itemToJsonObject(list.get(i)));
        }

        try {
            prefJson.put(KEY_CONTACTS_LIST, itemList.toString());

            SharedPreferences prefs = context.getSharedPreferences(
                    PREF_NAME, Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(KEY_CONTACTS_LIST, itemList.toString());
            editor.apply();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<MyContact> getListFromPref(Context context,
                                                       String KEY_CONTACTS_LIST) {
        ArrayList<MyContact> list = new ArrayList<>();

        SharedPreferences prefs = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);

        String prefListString = prefs.getString(KEY_CONTACTS_LIST, "");

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

    public static boolean updateListPref(Context context, MyContact myContact,
                                         String KEY_CONTACTS_LIST) {

        SharedPreferences prefs = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);

        String prefListString = prefs.getString(KEY_CONTACTS_LIST, "");

        if (!prefListString.isEmpty()) {
            try {
                JSONArray prefList = new JSONArray(prefListString);
                for (int i = 0; i < prefList.length(); i++) {
                    if (jsonObjectToItem(prefList.getJSONObject(i)).getId().equals(myContact.getId())) {
                        prefList.put(i, itemToJsonObject(myContact));
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(KEY_CONTACTS_LIST, prefList.toString());
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

    public static boolean deleteListPref(Context context, MyContact myContact,
                                         String KEY_CONTACTS_LIST) {

        SharedPreferences prefs = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);

        String prefListString = prefs.getString(KEY_CONTACTS_LIST, "");

        if (!prefListString.isEmpty()) {
            try {
                JSONArray prefList = new JSONArray(prefListString);
                for (int i = 0; i < prefList.length(); i++) {
                    if (jsonObjectToItem(prefList.getJSONObject(i)).getId().equals(myContact.getId())) {
                        prefList.remove(i);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(KEY_CONTACTS_LIST, prefList.toString());
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



}
