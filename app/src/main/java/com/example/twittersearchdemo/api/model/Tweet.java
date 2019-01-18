package com.example.twittersearchdemo.api.model;

import com.google.gson.annotations.SerializedName;

public class Tweet {

    @SerializedName("created_at")
    public String dateCreated;

    @SerializedName("id")
    public String id;

    @SerializedName("text")
    public String text;

    @SerializedName("retweet_count")
    public String retweet_count;

    @SerializedName("favorite_count")
    public String favorite_count;

    @SerializedName("user")
    public TwitterUser user;

}
