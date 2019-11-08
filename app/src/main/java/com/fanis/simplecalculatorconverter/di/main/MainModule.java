package com.fanis.simplecalculatorconverter.di.main;

import com.fanis.simplecalculatorconverter.network.FixerApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public abstract class MainModule {

    @Provides
    static FixerApi provideFixerApi(Retrofit retrofit) {
        return retrofit.create(FixerApi.class);
    }
}