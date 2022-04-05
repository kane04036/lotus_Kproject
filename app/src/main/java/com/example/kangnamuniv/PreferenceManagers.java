package com.example.kangnamuniv;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class PreferenceManagers {
    private static final String SETTINGS_PLAYER_JSON = "settings_item_json";


    //    private static final String DEFAULT_VALUE_STRING = "";
//    private static final boolean DEFAULT_VALUE_BOOLEAN = false;
//    private static final int DEFAULT_VALUE_INT = -1;
//    private static final long DEFAULT_VALUE_LONG = -1L;
//    private static final float DEFAULT_VALUE_FLOAT = -1F;
//
//
//    private static SharedPreferences getPreferences(Context context) {
//        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
//    }
//
//    public static void setString(Context context, String key, String value) {
//        SharedPreferences prefs = getPreferences(context);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(key, value);
//        editor.commit();
//    }
//
//    public static String getString(Context context, String key) {
//        SharedPreferences prefs = getPreferences(context);
//        String value = prefs.getString(key, DEFAULT_VALUE_STRING);
//        return value;
//    }
    public void setStringArrayPref(Context context, String key, ArrayList<String> values) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();

        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }

        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }

        editor.apply();
    }

    public ArrayList getStringArrayPref(Context context, String key) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);
        ArrayList urls = new ArrayList();

        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);

                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

    public void setIntegerArrayPref(Context context, String key, ArrayList<Integer> values) {

        SharedPreferences prefsInt = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefsInt.edit();
        JSONArray a = new JSONArray();

        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }

        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }

        editor.apply();
    }

    public ArrayList getIntegerArrayPref(Context context, String key) {

        SharedPreferences prefsInt = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefsInt.getString(key, null);
        ArrayList urls = new ArrayList();

        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);

                for (int i = 0; i < a.length(); i++) {
                    int url = a.optInt(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

}
