package com.example.twittersearchdemo.di.module;

import android.content.Context;

import com.example.twittersearchdemo.MyApp;
import com.example.twittersearchdemo.di.scope.AppScope;
import com.example.twittersearchdemo.local.SharedPrefDataManager;
import com.example.twittersearchdemo.local.SharedPrefHelper;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPrefModule {

    @AppScope
    @Provides
    SharedPrefHelper provideSharedPrefHelper(MyApp app) {
        return new SharedPrefHelper(app.getSharedPreferences("TWITTER_SEARCH_PREF", Context.MODE_PRIVATE));
    }

    @AppScope
    @Provides
    SharedPrefDataManager provideSharedPrefDataManager(SharedPrefHelper helper) {
        return new SharedPrefDataManager(helper);
    }
}
