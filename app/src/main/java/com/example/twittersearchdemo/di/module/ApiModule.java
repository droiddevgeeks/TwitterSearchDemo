package com.example.twittersearchdemo.di.module;

import com.example.twittersearchdemo.api.ApiConstants;
import com.example.twittersearchdemo.api.TwitterApi;
import com.example.twittersearchdemo.di.scope.AppScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    @AppScope
    @Provides
    Retrofit provideRetrofit() {
        return new Retrofit.Builder().baseUrl(ApiConstants.TWITTER_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @AppScope
    @Provides
    TwitterApi provideTwitterApi(Retrofit retrofit) {
        return retrofit.create(TwitterApi.class);
    }
}
