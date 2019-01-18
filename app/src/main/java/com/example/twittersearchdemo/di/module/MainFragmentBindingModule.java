package com.example.twittersearchdemo.di.module;

import com.example.twittersearchdemo.ui.fragment.SearchFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBindingModule {

    @ContributesAndroidInjector
    abstract SearchFragment provideListFragment();
}
