package com.benX.trackerapp._di.module;

import android.content.Context;

import com.benX.trackerapp.TrackerApp;
import com.benX.trackerapp._di.scopes.ActivityScope;
import com.benX.trackerapp.core.helpers.MapHelper;
import com.benX.trackerapp.core.helpers.UIHelper;
import com.benX.trackerapp.core.listeners.schedulers.AppRxSchedulers;
import com.benX.trackerapp.main.home.HomeActivity;
import com.benX.trackerapp.main.home.rvvm.HomeViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import dagger.Module;
import dagger.Provides;


@Module
public class HomeModule {

    @ActivityScope
    @Provides
    HomeViewModel provideHomeViewModel(HomeActivity homeActivity, AppRxSchedulers appRxSchedulers, UIHelper uiHelper,
                                       FusedLocationProviderClient locationProviderClient, MapHelper mapHelper) {

        return new HomeViewModel(homeActivity, appRxSchedulers, uiHelper.getLocationRequest(), locationProviderClient, mapHelper);
    }

    @ActivityScope
    @Provides
    MapHelper provideMapHelper(HomeActivity context) {
        return new MapHelper(context.getResources());
    }


    @ActivityScope
    @Provides
    AppRxSchedulers provideAppRxSchedulers() {
        return new AppRxSchedulers();
    }

    @ActivityScope
    @Provides
    FusedLocationProviderClient provideFusedLocationProviderClient(HomeActivity homeActivity) {
        return LocationServices.getFusedLocationProviderClient(homeActivity);
    }

}
