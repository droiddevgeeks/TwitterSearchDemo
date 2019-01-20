package com.example.twittersearchdemo.api;


public class ApiConstants {

    private final static String CONSUMER_KEY = "Replace your";
    private final static String CONSUMER_SECRET = "Replace yours";
    public final static String TWITTER_BASE_URL = "https://api.twitter.com";
    public static final String BEARER_TOKEN_CREDENTIALS = CONSUMER_KEY + ":" + CONSUMER_SECRET;
    public final static String TWITTER_HASHTAG_URL = "/1.1/search/tweets.json";
    public final static String TWITTER_TOKEN_URL = "/oauth2/token";

}
