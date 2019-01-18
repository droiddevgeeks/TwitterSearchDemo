package com.example.twittersearchdemo.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.twittersearchdemo.R;
import com.example.twittersearchdemo.api.TwitterViewModelFactory;
import com.example.twittersearchdemo.base.BaseFragment;
import com.example.twittersearchdemo.local.SharedPrefDataManager;
import com.example.twittersearchdemo.ui.adapter.SearchAdapter;
import com.example.twittersearchdemo.ui.viewmodel.SearchViewModel;
import com.example.twittersearchdemo.util.RxSearchObservable;
import com.example.twittersearchdemo.util.Utilities;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

import static android.widget.LinearLayout.HORIZONTAL;

public class SearchFragment extends BaseFragment {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private ProgressBar progressBar;

    @Inject
    SharedPrefDataManager localDataManager;
    @Inject
    TwitterViewModelFactory viewModelFactory;
    private SearchViewModel viewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        searchView = (SearchView) view.findViewById(R.id.sv_tweet);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_tweet);

        adapter = new SearchAdapter(getContext(), new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), HORIZONTAL));

        observeSearchView();
        observeDataChange();
    }

    private void observeSearchView() {
        RxSearchObservable.fromview(searchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(text -> !text.isEmpty())
                .map(text -> text.toLowerCase().trim())
                .distinctUntilChanged()
                .subscribe(query -> {
                    if (query.length() >= 3) {
                        if (Utilities.isNetworkConnected(getContext())) {
                            if (localDataManager.getAccessToken().isEmpty()) {
                                viewModel.fetchAccessToken();
                            } else {
                                viewModel.fetchTweet(localDataManager.getAccessToken(), query);
                            }
                        } else
                            showToast(getContext(), getString(R.string.internet_error));
                    }
                });
    }

    private void observeDataChange() {


        viewModel.apiTokenData().observe(this, token -> {
            localDataManager.setAccessToken(token.access_token);
            viewModel.fetchTweet(token.access_token, searchView.getQuery().toString());
        });

        viewModel.apiError().observe(this, error -> {
            showToast(getContext(), getString(R.string.some_error));
        });


        viewModel.loading().observe(this, isLoading -> {
            if (isLoading)
                progressBar.setVisibility(View.VISIBLE);
            else
                progressBar.setVisibility(View.GONE);
        });

        viewModel.apiTweetData().observe(this, tweetList -> {
            adapter.addTweetList(tweetList.tweets);
            recyclerView.setAdapter(adapter);

        });
    }


}
