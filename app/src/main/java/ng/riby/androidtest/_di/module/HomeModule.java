package ng.riby.androidtest._di.module;

import ng.riby.androidtest._di.scopes.ActivityScope;
import ng.riby.androidtest.core.helpers.MapHelper;
import ng.riby.androidtest.core.helpers.UIHelper;
import ng.riby.androidtest.core.listeners.schedulers.AppRxSchedulers;
import ng.riby.androidtest.main.home.HomeActivity;
import ng.riby.androidtest.main.home.rvvm.HomeViewModel;

import com.google.android.gms.location.FusedLocationProviderClient;
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
