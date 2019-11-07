package com.fanis.simplecalculatorconverter.di;

import androidx.lifecycle.ViewModelProvider;

import com.fanis.simplecalculatorconverter.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

//This module is responsible for generating(doing) the dependency injection for the factory class
@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory modelProviderFactory);
}
