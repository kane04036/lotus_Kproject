/*package com.example.kangnamuniv;

import android.content.Context;

import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PreferenceManager {
    public static void saveSharedPreferences_Data(Context context, String key, String[] list) {

        SharedPreferences pref = context.getSharedPreferences("prf", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        Set<String> set = new HashSet<String>();
        set.addAll(Arrays.asList(list));
        edit.putStringSet(key, set);
        edit.commit();

    }


    //꺼내기

    public static ArrayList<String> loadSharedPreferencesData(Context context, String key) {


        SharedPreferences pref = context.getSharedPreferences("prf", Context.MODE_PRIVATE);
        Set<String> set = pref.getStringSet(key, null);
        return new set;

    }

}*/
