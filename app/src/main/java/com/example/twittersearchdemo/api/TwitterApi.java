package com.example.twittersearchdemo.api;

import com.example.twittersearchdemo.api.model.TokenType;
import com.example.twittersearchdemo.api.model.TweetList;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TwitterApi {

    @GET(ApiConstants.TWITTER_HASHTAG_URL)
    Single<TweetList> getTweetList(@Header("Authorization") String authorization, @Query("q") String hashtag, @Query("count")String rpp);

    @FormUrlEncoded
    @POST(ApiConstants.TWITTER_TOKEN_URL)
    Single<TokenType> getToken(@Header("Authorization") String authorization, @Field("grant_type") String grantType);
}
