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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static android.widget.LinearLayout.HORIZONTAL;

public class SearchFragment extends BaseFragment implements View.OnClickListener {

    private SearchView tweetSearchView;
    private LinearLayout tweetSort;

    private SearchAdapter tweetSearchAdapter;
    private ProgressBar progressBar;
    private Disposable disposable;
    private SearchViewModel searchViewModel;

    @Inject
    SharedPrefDataManager localDataManager;
    @Inject
    TwitterViewModelFactory viewModelFactory;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);
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
        tweetSearchView = (SearchView) view.findViewById(R.id.sv_tweet);
        tweetSort = (LinearLayout) view.findViewById(R.id.ll_sort);
        RecyclerView tweetRecyclerView = (RecyclerView) view.findViewById(R.id.rv_tweet);
        TextView tweetSortUp = (TextView) view.findViewById(R.id.tv_sort_up);
        TextView tweetSortDown = (TextView) view.findViewById(R.id.tv_sort_down);

        tweetSearchAdapter = new SearchAdapter(getContext(), new ArrayList<>());
        tweetRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tweetRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), HORIZONTAL));
        tweetRecyclerView.setAdapter(tweetSearchAdapter);


        tweetSortUp.setOnClickListener(this);
        tweetSortDown.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        observeSearchView();
        observeDataChange();
    }

    private void observeSearchView() {

        disposable = RxSearchObservable.fromview(tweetSearchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(text -> !text.isEmpty() && text.length() >= 3)
                .map(text -> text.toLowerCase().trim())
                .distinctUntilChanged()
                .switchMap(s -> Observable.just(s))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query ->
                        {
                            if (Utilities.isNetworkConnected(getContext())) {
                                if (localDataManager.getAccessToken().isEmpty()) {
                                    searchViewModel.fetchAccessToken();
                                } else {
                                    searchViewModel.fetchTweet(localDataManager.getAccessToken(), query);
                                }
                            } else
                                showToast(getContext(), getString(R.string.internet_error));
                        }
                );
    }

    private void observeDataChange() {


        searchViewModel.apiTokenData().observe(getViewLifecycleOwner(), token -> {
            localDataManager.setAccessToken(token.access_token);
            searchViewModel.fetchTweet(token.access_token, tweetSearchView.getQuery().toString());
        });

        searchViewModel.apiError().observe(getViewLifecycleOwner(), error -> {
            showToast(getContext(), getString(R.string.some_error));
        });


        searchViewModel.loading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading)
                progressBar.setVisibility(View.VISIBLE);
            else
                progressBar.setVisibility(View.GONE);
        });

        searchViewModel.apiTweetData().observe(getViewLifecycleOwner(), tweetList -> {
            tweetSort.setVisibility(View.VISIBLE);
            tweetSearchAdapter.addTweetList(tweetList.tweets);
        });

        searchViewModel.sortTweetData().observe(getViewLifecycleOwner(), sortTweetList -> {
            tweetSearchAdapter.addTweetList(sortTweetList);
        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_sort_up:
                searchViewModel.sortTweetByPopularity(true);
                break;
            case R.id.tv_sort_down:
                searchViewModel.sortTweetByPopularity(false);
                break;
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (disposable != null) disposable.dispose();
    }

}
