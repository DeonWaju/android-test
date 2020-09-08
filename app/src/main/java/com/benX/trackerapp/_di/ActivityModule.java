package com.benX.trackerapp._di;


import com.benX.trackerapp._di.module.HomeModule;
import com.benX.trackerapp._di.scopes.ActivityScope;
import com.benX.trackerapp.main.home.HomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = {HomeModule.class})
    abstract HomeActivity bindHomeActivity();

}
