package com.benX.trackerapp._di;

import com.benX.trackerapp.TrackerApp;
import com.benX.trackerapp._di.module.ContributeActivityModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,
        AppModule.class, ContributeActivityModule.class})
public interface AppComponent {

    void inject(TrackerApp trackerApp);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(TrackerApp app);

        AppComponent build();
    }
}
