package com.example.twittersearchdemo.api.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TweetTest {

    private final String name = "Testing Twitter name";
    private final String text = "Testing Twitter text";
    private final String retweetCount = "20";
    private final String favCount = "10";

    @Mock
    Tweet tweet;
    @Mock
    TwitterUser user;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(user.getName()).thenReturn(name);
        Mockito.when(tweet.getText()).thenReturn(text);
        Mockito.when(tweet.getRetweet_count()).thenReturn(retweetCount);
        Mockito.when(tweet.getFavorite_count()).thenReturn(favCount);
    }

    @Test
    public void testTweetUserName() {
        Mockito.when(user.getName()).thenReturn(name);
        Assert.assertEquals("Testing Twitter name", user.getName());
    }

    @Test
    public void testTweetUserText() {
        Mockito.when(tweet.getText()).thenReturn(text);
        Assert.assertEquals("Testing Twitter text", tweet.getText());
    }

    @Test
    public void testTweetCount() {
        Mockito.when(tweet.getRetweet_count()).thenReturn(retweetCount);
        Assert.assertEquals("20", tweet.getRetweet_count());
    }

    @Test
    public void testTweetFavouriteCount() {
        Mockito.when(tweet.getFavorite_count()).thenReturn(favCount);
        Assert.assertEquals("10", tweet.getFavorite_count());
    }


    @Test
    public void testTweetUserNameWrong() {
        Mockito.when(user.getName()).thenReturn(name);
        Assert.assertNotEquals("Twitter name", user.getName());
    }

    @Test
    public void testTweetUserTextWrong() {
        Mockito.when(tweet.getText()).thenReturn(text);
        Assert.assertNotEquals("Twitter text", tweet.getText());
    }

    @Test
    public void testTweetCountWrong() {
        Mockito.when(tweet.getRetweet_count()).thenReturn(retweetCount);
        Assert.assertNotEquals("10", tweet.getRetweet_count());
    }

    @Test
    public void testTweetFavouriteCountWrong() {
        Mockito.when(tweet.getFavorite_count()).thenReturn(favCount);
        Assert.assertNotEquals("30", tweet.getFavorite_count());
    }

    @After
    public void clearResource() {
        tweet = null;
        user = null;
    }
}
