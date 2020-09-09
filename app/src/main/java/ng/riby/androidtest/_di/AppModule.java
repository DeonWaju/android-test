package ng.riby.androidtest._di;

import android.content.Context;

import ng.riby.androidtest.AndroidTracker;
import ng.riby.androidtest._di.scopes.ActivityScope;
import ng.riby.androidtest.core.data.manager.DatabaseManager;
import ng.riby.androidtest.core.helpers.UIHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    Context provideContext(AndroidTracker application) {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    DatabaseManager provideDatabaseManager(AndroidTracker app) {
        return DatabaseManager.getInstance(app.getApplicationContext());
    }

    @ActivityScope
    @Provides
    UIHelper provideUIHelper(AndroidTracker app) {
        return new UIHelper(app.getApplicationContext());
    }
}
