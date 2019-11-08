package com.fanis.simplecalculatorconverter.ui;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.fanis.simplecalculatorconverter.database.CurrencyEntity;
import com.fanis.simplecalculatorconverter.models.CurrencyDTO;
import com.fanis.simplecalculatorconverter.network.FixerApi;
import com.fanis.simplecalculatorconverter.repository.CurrencyRepository;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";
    private CurrencyRepository currencyRepository;

    //inject
    private final FixerApi fixerApi;
    private MediatorLiveData<MainResource<CurrencyDTO>> allCurrencies = new MediatorLiveData<>();
    private LiveData<CurrencyEntity> offlineAllCurrencies;

    @Inject
    public MainViewModel(FixerApi fixerApi, Application application) {
        this.fixerApi = fixerApi;
        currencyRepository = new CurrencyRepository(application, fixerApi);
        offlineAllCurrencies = currencyRepository.getGetAllCurrencies();
    }

    public void getAllCurrencies() {
        observeResult(queryCurrencies());
    }

    private LiveData<MainResource<CurrencyDTO>> queryCurrencies() {
        return currencyRepository.fetchFromFixerApiService();
    }

    public void observeResult(final LiveData<MainResource<CurrencyDTO>> source) {
        if (allCurrencies != null) {

            allCurrencies.setValue(MainResource.loading((CurrencyDTO) null));
            allCurrencies.addSource(source, repoResource -> {
                allCurrencies.setValue(repoResource);
                allCurrencies.removeSource(source);
            });
        }
    }

    public LiveData<MainResource<CurrencyDTO>> observeCurrencies() {
        return allCurrencies;
    }

    public LiveData<CurrencyEntity> getCurrenciesFromDB() {
        return offlineAllCurrencies;
    }
}
