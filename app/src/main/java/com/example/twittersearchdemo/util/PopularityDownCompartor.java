package com.example.twittersearchdemo.util;

import com.example.twittersearchdemo.api.model.Tweet;

import java.util.Comparator;

public class PopularityDownCompartor implements Comparator<Tweet> {
    @Override
    public int compare(Tweet o1, Tweet o2) {
        return Integer.valueOf(o2.retweet_count) - Integer.valueOf(o1.retweet_count);
    }
}
