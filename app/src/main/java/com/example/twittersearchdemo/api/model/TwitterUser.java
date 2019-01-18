package com.example.twittersearchdemo.api.model;

import com.google.gson.annotations.SerializedName;

public class TwitterUser {

    @SerializedName("screen_name")
    public String screenName;

    @SerializedName("name")
    public String name;

    @SerializedName("profile_image_url_https")
    public String profileImageUrl;
}
