package com.example.twittersearchdemo.local;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class SharedPrefHelper {

    private SharedPreferences preferences;

    @Inject
    public SharedPrefHelper(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    private final String ACCESS_TOKEN = "access_token";

    public void setTOKEN(String token) {
        preferences.edit().putString(ACCESS_TOKEN, token).apply();
    }

    public String getTOKEN() {
        return preferences.getString(ACCESS_TOKEN, "");
    }
}
