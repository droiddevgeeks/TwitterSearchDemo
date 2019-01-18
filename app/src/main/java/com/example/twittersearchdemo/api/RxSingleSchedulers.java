package com.example.twittersearchdemo.api;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public interface RxSingleSchedulers {
  RxSingleSchedulers DEFAULT = new RxSingleSchedulers() {
    @Override
    public <T> SingleTransformer<T, T> applySchedulers() {
      return new SingleTransformer<T, T>() {
        @Override
        public SingleSource<T> apply(final Single<T> single) {
          return single
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread());
        }
      };
    }
  };

  RxSingleSchedulers TEST = new RxSingleSchedulers() {
    @Override
    public <T> SingleTransformer<T, T> applySchedulers() {
      return single -> single;
    }
  };
  <T> SingleTransformer<T, T> applySchedulers();
}
