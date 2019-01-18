package com.example.twittersearchdemo.di.component;

import com.example.twittersearchdemo.MyApp;
import com.example.twittersearchdemo.di.module.ActivityBindingModule;
import com.example.twittersearchdemo.di.module.ApiModule;
import com.example.twittersearchdemo.di.module.ApplicationModule;
import com.example.twittersearchdemo.di.module.SharedPrefModule;
import com.example.twittersearchdemo.di.module.ViewModelModule;
import com.example.twittersearchdemo.di.scope.AppScope;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@AppScope
@Component(modules = {ApplicationModule.class,
        AndroidSupportInjectionModule.class,
        ActivityBindingModule.class,
        ApiModule.class ,
        SharedPrefModule.class})
public interface ApplicationComponent extends AndroidInjector<MyApp> {

    void inject(MyApp application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(MyApp application);

        ApplicationComponent build();
    }
}