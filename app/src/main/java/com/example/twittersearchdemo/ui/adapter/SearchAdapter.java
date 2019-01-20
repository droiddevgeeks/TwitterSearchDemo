package com.example.twittersearchdemo.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.twittersearchdemo.api.model.Tweet;
import com.example.twittersearchdemo.databinding.TweetRowItemsBinding;
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
        if (!this.tweetList.isEmpty()) {
            this.tweetList.clear();
        }
        this.tweetList.addAll(tweetList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TweetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TweetViewHolder(TweetRowItemsBinding.inflate(layoutInflater, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TweetViewHolder tweetViewHolder, int i) {

        Tweet tweet = tweetList.get(i);
        tweetViewHolder.binding.setTweet(tweet);
        Picasso.with(context).load(tweet.user.profileImageUrl).into(tweetViewHolder.binding.imgProfile);
    }

    @Override
    public int getItemCount() {
        return tweetList.size();
    }

    static final class TweetViewHolder extends RecyclerView.ViewHolder {

        private final TweetRowItemsBinding binding;

        public TweetViewHolder(TweetRowItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
