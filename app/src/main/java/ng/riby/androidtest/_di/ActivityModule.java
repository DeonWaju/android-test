package ng.riby.androidtest._di;


import ng.riby.androidtest._di.module.HomeModule;
import ng.riby.androidtest._di.scopes.ActivityScope;
import ng.riby.androidtest.main.home.HomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = {HomeModule.class})
    abstract HomeActivity bindHomeActivity();

}
