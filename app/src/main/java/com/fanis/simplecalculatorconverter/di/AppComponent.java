package com.fanis.simplecalculatorconverter.di;

import android.app.Application;

import com.fanis.simplecalculatorconverter.BaseApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuildersModule.class,
                AppModule.class,
                ViewModelFactoryModule.class,
        })


public interface AppComponent extends AndroidInjector<BaseApplication> {

    @Component.Builder
    interface  Builder{

        //Something you can use if you want to bind a particular object or instance of the object to the component the time of the construction
        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }
}