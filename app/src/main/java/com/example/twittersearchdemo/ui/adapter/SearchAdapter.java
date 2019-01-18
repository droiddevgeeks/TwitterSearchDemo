package com.example.twittersearchdemo.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.twittersearchdemo.R;
import com.example.twittersearchdemo.api.model.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.TweetViewHolder> {

    private List<Tweet> tweetList;
    private Context context;
    private LayoutInflater layoutInflater;

    public SearchAdapter(Context context, List<Tweet> tweetList) {
        this.tweetList = tweetList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }


    public void addTweetList(List<Tweet> tweetList) {
        this.tweetList = tweetList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TweetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.tweet_row_items, viewGroup, false);
        return new TweetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TweetViewHolder tweetViewHolder, int i) {

        Tweet tweet = tweetList.get(i);
        tweetViewHolder.name.setText(tweet.user.name + " ( " + tweet.user.screenName + " ) ");
        tweetViewHolder.tweetText.setText(tweet.text);
        tweetViewHolder.retweetCount.setText(tweet.retweet_count);
        tweetViewHolder.favcount.setText(tweet.favorite_count);
        Picasso.with(context).load(tweet.user.profileImageUrl).into(tweetViewHolder.profileImage);
    }

    @Override
    public int getItemCount() {
        return tweetList.size();
    }

    static final class TweetViewHolder extends RecyclerView.ViewHolder {

        private ImageView profileImage;
        private TextView name;
        private TextView tweetText;
        private TextView retweetCount;
        private TextView favcount;

        public TweetViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.tv_Name);
            profileImage = (ImageView) itemView.findViewById(R.id.img_profile);
            tweetText = (TextView) itemView.findViewById(R.id.tv_tweet);
            retweetCount = (TextView) itemView.findViewById(R.id.tv_retweet_count);
            favcount = (TextView) itemView.findViewById(R.id.tv_fav_count);
        }
    }
}
