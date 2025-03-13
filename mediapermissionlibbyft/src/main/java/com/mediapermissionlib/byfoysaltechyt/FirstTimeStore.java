package com.mediapermissionlib.byfoysaltechyt;

import android.content.Context;
import android.content.SharedPreferences;

public class FirstTimeStore {
    private static final String PREF_NAME = "PermissionLibPrefs";
    private static final String KEY_FIRST_TIME = "firstTimeData";
    private final Context context;

    public FirstTimeStore(Context context) {
        this.context = context;
    }

    public void storeFirstTime(int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_FIRST_TIME, value);
        editor.apply();
    }

    public int loadFirstTime() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_FIRST_TIME, 0);
    }

    public boolean isFirstTime() {
        return loadFirstTime() == 0;
    }
}
