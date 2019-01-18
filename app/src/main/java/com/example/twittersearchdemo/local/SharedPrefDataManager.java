package com.example.twittersearchdemo.local;

import javax.inject.Inject;

public class SharedPrefDataManager {

    private SharedPrefHelper helper;

    @Inject
    public SharedPrefDataManager(SharedPrefHelper helper) {
        this.helper = helper;
    }

    public void setAccessToken(String token) {
        helper.setTOKEN(token);
    }

    public String getAccessToken() {
        return helper.getTOKEN();
    }
}
