package com.example.twittersearchdemo.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.twittersearchdemo.api.TwitterViewModelFactory;
import com.example.twittersearchdemo.di.scope.ViewModelKey;
import com.example.twittersearchdemo.ui.viewmodel.SearchViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    abstract ViewModel bindSearchViewModel(SearchViewModel searchViewModel);


    @Binds
    abstract ViewModelProvider.Factory bindTwitterViewModelFactory(TwitterViewModelFactory factory);
}
