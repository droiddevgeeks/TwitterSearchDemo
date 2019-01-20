package com.example.twittersearchdemo.api.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TwitterUserTest {

    private final String name = "Testing Twitter name";
    private final String screenName = "Testing Twitter Screen Name";
    private final String profileURL = "www.google.com/mypic";

    @Mock
    TwitterUser user;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(user.getName()).thenReturn(name);
        Mockito.when(user.getScreenName()).thenReturn(screenName);
        Mockito.when(user.getProfileImageUrl()).thenReturn(profileURL);

    }

    @Test
    public void testTweetUserName() {
        Mockito.when(user.getName()).thenReturn(name);
        Assert.assertEquals("Testing Twitter name", user.getName());
    }

    @Test
    public void testTweetUserScreenName() {
        Mockito.when(user.getScreenName()).thenReturn(screenName);
        Assert.assertEquals("Testing Twitter Screen Name", user.getScreenName());
    }

    @Test
    public void testTweetUserProfileUrl() {
        Mockito.when(user.getProfileImageUrl()).thenReturn(profileURL);
        Assert.assertEquals("www.google.com/mypic", user.getProfileImageUrl());
    }


    @Test
    public void testTweetUserNameWrong() {
        Mockito.when(user.getName()).thenReturn(name);
        Assert.assertNotEquals("Twitter name", user.getName());
    }

    @Test
    public void testTweetUserScreenNameWrong() {
        Mockito.when(user.getScreenName()).thenReturn(screenName);
        Assert.assertNotEquals(" Twitter Screen Name", user.getScreenName());
    }

    @Test
    public void testTweetUserProfileUrlWrong() {
        Mockito.when(user.getProfileImageUrl()).thenReturn(profileURL);
        Assert.assertNotEquals("www.google.com", user.getProfileImageUrl());
    }

    @After
    public void clearResource() {
        user = null;
    }


}
