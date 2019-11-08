package com.fanis.simplecalculatorconverter.di;

import com.fanis.simplecalculatorconverter.ui.MainActivity;
import com.fanis.simplecalculatorconverter.di.main.MainModule;
import com.fanis.simplecalculatorconverter.di.main.MainViewModelsModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {MainViewModelsModule.class, MainModule.class}
    )

    abstract MainActivity contributeMainActivity();
}