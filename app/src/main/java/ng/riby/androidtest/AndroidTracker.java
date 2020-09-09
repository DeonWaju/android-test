package ng.riby.androidtest;

import android.app.Activity;
import android.app.Application;

import ng.riby.androidtest._di.DaggerAppComponent;
import ng.riby.androidtest.core.data.manager.DatabaseManager;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class AndroidTracker extends Application implements HasActivityInjector {

    private static final String TAG = AndroidTracker.class.getSimpleName();


    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    private static AndroidTracker mInstance;


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


    public static synchronized AndroidTracker getInstance() {
        return mInstance;
    }


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
