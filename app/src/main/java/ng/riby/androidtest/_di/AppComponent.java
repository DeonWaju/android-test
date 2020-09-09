package ng.riby.androidtest._di;

import ng.riby.androidtest.AndroidTracker;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import ng.riby.androidtest._di.module.ContributeActivityModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,
        AppModule.class, ContributeActivityModule.class})
public interface AppComponent {

    void inject(AndroidTracker androidTracker);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(AndroidTracker app);

        AppComponent build();
    }
}
