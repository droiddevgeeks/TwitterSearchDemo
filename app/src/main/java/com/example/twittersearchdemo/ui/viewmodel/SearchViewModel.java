package com.example.twittersearchdemo.ui.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.twittersearchdemo.api.ApiConstants;
import com.example.twittersearchdemo.api.RxSingleSchedulers;
import com.example.twittersearchdemo.api.TwitterApiClient;
import com.example.twittersearchdemo.api.model.TokenType;
import com.example.twittersearchdemo.api.model.TweetList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

import static com.example.twittersearchdemo.util.Utilities.getBase64String;

public class SearchViewModel extends ViewModel {

    private CompositeDisposable disposable;
    private final TwitterApiClient apiClient;

    private final MutableLiveData<TokenType> apiTokenData = new MutableLiveData<>();
    private final MutableLiveData<TweetList> apiTweetData = new MutableLiveData<>();
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
                .getTweetList("Bearer " + token, query)
                .doOnEvent((not, used) -> loading.postValue(false))
                .compose(RxSingleSchedulers.DEFAULT.applySchedulers())
                .subscribe(
                        apiTweetData::postValue,
                        apiError::postValue
                )
        );

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }
}
