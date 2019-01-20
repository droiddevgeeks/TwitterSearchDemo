package com.example.twittersearchdemo.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.twittersearchdemo.R;
import com.example.twittersearchdemo.api.TwitterViewModelFactory;
import com.example.twittersearchdemo.base.BaseFragment;
import com.example.twittersearchdemo.databinding.FragmentSearchBinding;
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

public class SearchFragment extends BaseFragment<FragmentSearchBinding> {

    private SearchAdapter tweetSearchAdapter;
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

    @Override
    public int getLayout() {
        return R.layout.fragment_search;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tweetSearchAdapter = new SearchAdapter(getContext(), new ArrayList<>());
        binding.rvTweet.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvTweet.addItemDecoration(new DividerItemDecoration(getContext(), HORIZONTAL));
        binding.rvTweet.setAdapter(tweetSearchAdapter);


        binding.setDownClickListener(click -> searchViewModel.sortTweetByPopularity(false));
        binding.setUpClickListener(click -> searchViewModel.sortTweetByPopularity(true));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        observeSearchView();
        observeDataChange();
    }

    private void observeSearchView() {

        disposable = RxSearchObservable.fromview(binding.svTweet)
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
            searchViewModel.fetchTweet(token.access_token, binding.svTweet.getQuery().toString());
        });

        searchViewModel.apiError().observe(getViewLifecycleOwner(), error -> {
            showToast(getContext(), getString(R.string.some_error));
        });


        searchViewModel.loading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading)
                binding.setShowLoading(true);
            else
                binding.setShowLoading(false);
        });

        searchViewModel.apiTweetData().observe(getViewLifecycleOwner(), tweetList -> {

            binding.setShowSorting(true);
            binding.svTweet.clearFocus();
            tweetSearchAdapter.addTweetList(tweetList.tweets);
        });

        searchViewModel.sortTweetData().observe(getViewLifecycleOwner(), sortTweetList -> {
            tweetSearchAdapter.addTweetList(sortTweetList);
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (disposable != null) disposable.dispose();
    }
}
