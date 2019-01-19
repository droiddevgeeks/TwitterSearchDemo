package com.example.twittersearchdemo.ui.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.twittersearchdemo.api.ApiConstants;
import com.example.twittersearchdemo.api.RxSingleSchedulers;
import com.example.twittersearchdemo.api.TwitterApiClient;
import com.example.twittersearchdemo.api.model.TokenType;
import com.example.twittersearchdemo.api.model.Tweet;
import com.example.twittersearchdemo.api.model.TweetList;
import com.example.twittersearchdemo.util.PopularityDownCompartor;
import com.example.twittersearchdemo.util.PopularityUpCompartor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.twittersearchdemo.util.Utilities.getBase64String;

public class SearchViewModel extends ViewModel {

    private CompositeDisposable disposable;
    private Disposable sortDispoable;
    private final TwitterApiClient apiClient;

    private final MutableLiveData<TokenType> apiTokenData = new MutableLiveData<>();
    private final MutableLiveData<TweetList> apiTweetData = new MutableLiveData<>();
    private final MutableLiveData<List<Tweet>> sortTweetData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> apiError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();


    @Inject
    public SearchViewModel(TwitterApiClient apiClient) {
        this.apiClient = apiClient;
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<TokenType> apiTokenData() {
        return apiTokenData;
    }

    public MutableLiveData<TweetList> apiTweetData() {
        return apiTweetData;
    }

    public MutableLiveData<List<Tweet>> sortTweetData() {
        return sortTweetData;
    }

    public MutableLiveData<Throwable> apiError() {
        return apiError;
    }

    public MutableLiveData<Boolean> loading() {
        return loading;
    }

    public void fetchAccessToken() {
        loading.postValue(true);
        disposable.add(apiClient
                .getToken("Basic " + getBase64String(ApiConstants.BEARER_TOKEN_CREDENTIALS), "client_credentials")
                .doOnEvent((not, used) -> loading.postValue(false))
                .compose(RxSingleSchedulers.DEFAULT.applySchedulers())
                .subscribe(
                        apiTokenData::postValue,
                        apiError::postValue
                )
        );
    }

    public void fetchTweet(String token, String query) {
        loading.postValue(true);
        disposable.add(apiClient
                .getTweetList("Bearer " + token, query, "100")
                .doOnEvent((not, used) -> loading.postValue(false))
                .compose(RxSingleSchedulers.DEFAULT.applySchedulers())
                .subscribe(
                        apiTweetData::postValue,
                        apiError::postValue
                )
        );

    }

    public void sortTweetByPopularity(boolean isAscending) {
        loading.postValue(true);
        sortDispoable = Observable.just(apiTweetData.getValue().tweets)
                .doOnComplete(() -> loading.postValue(false))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(unSortedList -> {

                    List<Tweet> sortedList = new ArrayList<>(unSortedList);
                    if (isAscending)
                        Collections.sort(sortedList, new PopularityDownCompartor());
                    else
                        Collections.sort(sortedList, new PopularityUpCompartor());
                    return sortedList;
                })
                .subscribe(
                        sortTweetData::postValue,
                        apiError::postValue
                );

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
        if (sortDispoable != null) {
            sortDispoable.dispose();
            sortDispoable = null;
        }
    }
}
