package com.benX.trackerapp;

import android.app.Activity;
import android.app.Application;

import com.benX.trackerapp._di.DaggerAppComponent;
import com.benX.trackerapp.core.data.manager.DatabaseManager;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class TrackerApp extends Application implements HasActivityInjector {

    private static final String TAG = TrackerApp.class.getSimpleName();


    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    private static TrackerApp mInstance;


    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);

        DatabaseManager databaseManager = DatabaseManager.getInstance(this);
        databaseManager.emptyDatabase();
    }


    public static synchronized TrackerApp getInstance() {
        return mInstance;
    }


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
