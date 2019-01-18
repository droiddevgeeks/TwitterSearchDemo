package com.example.twittersearchdemo.ui.activity;

import android.os.Bundle;

import com.example.twittersearchdemo.R;
import com.example.twittersearchdemo.base.BaseActivity;
import com.example.twittersearchdemo.ui.fragment.SearchFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchFragment fragment = new SearchFragment();
        replaceFragment(R.id.fragment_container,fragment, "SearchFragment");
    }
}
