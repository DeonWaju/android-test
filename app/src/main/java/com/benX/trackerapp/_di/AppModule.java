package com.benX.trackerapp._di;

import android.app.Application;
import android.content.Context;

import com.benX.trackerapp.TrackerApp;
import com.benX.trackerapp._di.scopes.ActivityScope;
import com.benX.trackerapp.core.data.manager.DatabaseManager;
import com.benX.trackerapp.core.helpers.MapHelper;
import com.benX.trackerapp.core.helpers.UIHelper;
import com.benX.trackerapp.core.listeners.schedulers.AppRxSchedulers;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    DatabaseManager provideDatabaseManager(TrackerApp app) {
        return DatabaseManager.getInstance(app.getApplicationContext());
    }

    @Singleton
    @Provides
    Context provideContext(TrackerApp application) {
        return application.getApplicationContext();
    }

    @ActivityScope
    @Provides
    UIHelper provideUIHelper(TrackerApp app) {
        return new UIHelper(app.getApplicationContext());
    }
}
