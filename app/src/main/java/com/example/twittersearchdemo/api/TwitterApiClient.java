package com.example.twittersearchdemo.api;

import com.example.twittersearchdemo.api.model.TokenType;
import com.example.twittersearchdemo.api.model.TweetList;

import javax.inject.Inject;

import io.reactivex.Single;

public class TwitterApiClient {

    private final TwitterApi api;

    @Inject
    public TwitterApiClient(TwitterApi api) {
        this.api = api;
    }

    public Single<TweetList> getTweetList(String authorization, String hashtag, String rpp) {
        return api.getTweetList(authorization, hashtag, rpp);
    }

    public Single<TokenType> getToken(String authorization, String grantType) {

        return api.getToken(authorization, grantType);
    }
}
