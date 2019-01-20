package com.example.twittersearchdemo.ui.viewmodel;

import com.example.twittersearchdemo.api.RxSingleSchedulers;
import com.example.twittersearchdemo.api.TwitterApiClient;
import com.example.twittersearchdemo.api.model.TokenType;
import com.example.twittersearchdemo.api.model.TweetList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subscribers.TestSubscriber;

public class SearchViewModelTest {

    @Mock
    TwitterApiClient twitterApiClient;

    private TestSubscriber<TweetList> testListSubscriber;
    private TestSubscriber<TokenType> testTokenSubscriber;
    private String demoResponse = "{\n" +
            "    \"statuses\": [\n" +
            "        {\n" +
            "            \"created_at\": \"Sun Feb 25 18:11:01 +0000 2018\",\n" +
            "            \"id\": 967824267948773377,\n" +
            "            \"id_str\": \"967824267948773377\",\n" +
            "            \"text\": \"From pilot to astronaut, Robert H. Lawrence was the first African-American to be selected as an astronaut by any na… https://t.co/FjPEWnh804\",\n" +
            "            \"truncated\": true,\n" +
            "            \"entities\": {\n" +
            "                \"hashtags\": [],\n" +
            "                \"symbols\": [],\n" +
            "                \"user_mentions\": [],\n" +
            "                \"urls\": [\n" +
            "                    {\n" +
            "                        \"url\": \"https://t.co/FjPEWnh804\",\n" +
            "                        \"expanded_url\": \"https://twitter.com/i/web/status/967824267948773377\",\n" +
            "                        \"display_url\": \"twitter.com/i/web/status/9…\",\n" +
            "                        \"indices\": [\n" +
            "                            117,\n" +
            "                            140\n" +
            "                        ]\n" +
            "                    }\n" +
            "                ]\n" +
            "            },\n" +
            "            \"metadata\": {\n" +
            "                \"result_type\": \"popular\",\n" +
            "                \"iso_language_code\": \"en\"\n" +
            "            },\n" +
            "            \"source\": \"<a href='\"https://www.sprinklr.com\"' rel='\"nofollow\"'>Sprinklr</a>\",\n" +
            "            \"in_reply_to_status_id\": null,\n" +
            "            \"in_reply_to_status_id_str\": null,\n" +
            "            \"in_reply_to_user_id\": null,\n" +
            "            \"in_reply_to_user_id_str\": null,\n" +
            "            \"in_reply_to_screen_name\": null,\n" +
            "            \"user\": {\n" +
            "                \"id\": 11348282,\n" +
            "                \"id_str\": \"11348282\",\n" +
            "                \"name\": \"NASA\",\n" +
            "                \"screen_name\": \"NASA\",\n" +
            "                \"location\": \"\",\n" +
            "                \"description\": \"Explore the universe and discover our home planet with @NASA. We usually post in EST (UTC-5)\",\n" +
            "                \"url\": \"https://t.co/TcEE6NS8nD\",\n" +
            "                \"entities\": {\n" +
            "                    \"url\": {\n" +
            "                        \"urls\": [\n" +
            "                            {\n" +
            "                                \"url\": \"https://t.co/TcEE6NS8nD\",\n" +
            "                                \"expanded_url\": \"http://www.nasa.gov\",\n" +
            "                                \"display_url\": \"nasa.gov\",\n" +
            "                                \"indices\": [\n" +
            "                                    0,\n" +
            "                                    23\n" +
            "                                ]\n" +
            "                            }\n" +
            "                        ]\n" +
            "                    },\n" +
            "                    \"description\": {\n" +
            "                        \"urls\": []\n" +
            "                    }\n" +
            "                },\n" +
            "                \"protected\": false,\n" +
            "                \"followers_count\": 28605561,\n" +
            "                \"friends_count\": 270,\n" +
            "                \"listed_count\": 90405,\n" +
            "                \"created_at\": \"Wed Dec 19 20:20:32 +0000 2007\",\n" +
            "                \"favourites_count\": 2960,\n" +
            "                \"utc_offset\": -18000,\n" +
            "                \"time_zone\": \"Eastern Time (US & Canada)\",\n" +
            "                \"geo_enabled\": false,\n" +
            "                \"verified\": true,\n" +
            "                \"statuses_count\": 50713,\n" +
            "                \"lang\": \"en\",\n" +
            "                \"contributors_enabled\": false,\n" +
            "                \"is_translator\": false,\n" +
            "                \"is_translation_enabled\": false,\n" +
            "                \"profile_background_color\": \"000000\",\n" +
            "                \"profile_background_image_url\": \"http://pbs.twimg.com/profile_background_images/590922434682880000/3byPYvqe.jpg\",\n" +
            "                \"profile_background_image_url_https\": \"https://pbs.twimg.com/profile_background_images/590922434682880000/3byPYvqe.jpg\",\n" +
            "                \"profile_background_tile\": false,\n" +
            "                \"profile_image_url\": \"http://pbs.twimg.com/profile_images/188302352/nasalogo_twitter_normal.jpg\",\n" +
            "                \"profile_image_url_https\": \"https://pbs.twimg.com/profile_images/188302352/nasalogo_twitter_normal.jpg\",\n" +
            "                \"profile_banner_url\": \"https://pbs.twimg.com/profile_banners/11348282/1518798395\",\n" +
            "                \"profile_link_color\": \"205BA7\",\n" +
            "                \"profile_sidebar_border_color\": \"000000\",\n" +
            "                \"profile_sidebar_fill_color\": \"F3F2F2\",\n" +
            "                \"profile_text_color\": \"000000\",\n" +
            "                \"profile_use_background_image\": true,\n" +
            "                \"has_extended_profile\": true,\n" +
            "                \"default_profile\": false,\n" +
            "                \"default_profile_image\": false,\n" +
            "                \"following\": null,\n" +
            "                \"follow_request_sent\": null,\n" +
            "                \"notifications\": null,\n" +
            "                \"translator_type\": \"regular\"\n" +
            "            },\n" +
            "            \"geo\": null,\n" +
            "            \"coordinates\": null,\n" +
            "            \"place\": null,\n" +
            "            \"contributors\": null,\n" +
            "            \"is_quote_status\": false,\n" +
            "            \"retweet_count\": 988,\n" +
            "            \"favorite_count\": 3875,\n" +
            "            \"favorited\": false,\n" +
            "            \"retweeted\": false,\n" +
            "            \"possibly_sensitive\": false,\n" +
            "            \"lang\": \"en\"\n" +
            "        }";


    @Before
    public void setUp() throws Exception {
        testListSubscriber = TestSubscriber.create();
        testTokenSubscriber = TestSubscriber.create();
        Mockito.when(twitterApiClient.getTweetList("","","1")).thenReturn(testDataObservable());
    }

    @After
    public void tearDown() throws Exception {

        twitterApiClient = null;
        testTokenSubscriber = null;
        testListSubscriber = null;
    }

    @Test
    public void fetchAccessToken() {
    }

    @Test
    public void fetchTweet() {
    }


    private Single<TweetList> testDataObservable() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return Single.fromObservable(Observable.just(gson.fromJson(demoResponse, TweetList.class)));
    }
}