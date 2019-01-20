package com.example.twittersearchdemo.util;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ViewBindings {


    @BindingAdapter("imageResource")
    public static void loadImage(final ImageView view, final String url) {
        Picasso.with(view.getContext())
                .load(url)
                .into(view);
    }
}
