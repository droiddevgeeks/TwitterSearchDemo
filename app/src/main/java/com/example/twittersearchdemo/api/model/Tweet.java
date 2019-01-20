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

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getRetweet_count() {
        return retweet_count;
    }

    public String getFavorite_count() {
        return favorite_count;
    }

    public TwitterUser getUser() {
        return user;
    }
}
