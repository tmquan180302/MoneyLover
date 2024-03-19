package com.poly.moneylover.network;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.poly.moneylover.models.User;

public class SharedPrefsManager {
    private static final String PREF_NAME = "MyAppPrefs";
    private static final String USER_KEY = "currentUser";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void saveUser(Context context, User user) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        editor.putString(USER_KEY, userJson);
        editor.apply();
    }

    public static User getUser(Context context) {
        Gson gson = new Gson();
        String userJson = getPrefs(context).getString(USER_KEY, null);
        return gson.fromJson(userJson, User.class);
    }

    public static void clearUser(Context context) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.remove(USER_KEY);
        editor.apply();
    }
}
