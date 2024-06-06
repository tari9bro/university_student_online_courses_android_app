package com.university.hafsa.controller;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class MySP {
    Activity activity;
    private SharedPreferences sharedPreferences;;

    public MySP(Activity activity) {
    this.activity = activity;
        sharedPreferences = activity.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
    }

    private SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = activity.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public String getMyString(String key) {
        return getSharedPreferences().getString(key, "");
    }

    public void saveMyString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }
}
