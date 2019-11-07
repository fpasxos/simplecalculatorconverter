package com.fanis.simplecalculatorconverter.di.main;

import androidx.lifecycle.ViewModel;

import com.fanis.simplecalculatorconverter.di.ViewModelKey;
import com.fanis.simplecalculatorconverter.ui.MainViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel bindMainViewModel(MainViewModel viewModel);
}
