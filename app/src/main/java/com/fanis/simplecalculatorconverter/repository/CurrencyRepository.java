package com.fanis.simplecalculatorconverter.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.fanis.simplecalculatorconverter.database.CurrencyDao;
import com.fanis.simplecalculatorconverter.database.CurrencyDatabase;
import com.fanis.simplecalculatorconverter.database.CurrencyEntity;
import com.fanis.simplecalculatorconverter.mapper.CurrencyMapper;
import com.fanis.simplecalculatorconverter.models.CurrencyDTO;
import com.fanis.simplecalculatorconverter.network.FixerApi;
import com.fanis.simplecalculatorconverter.ui.MainResource;
import com.fanis.simplecalculatorconverter.util.Constants;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CurrencyRepository {
    private CurrencyDao currencyDao;
    private LiveData<CurrencyEntity> getAllCurrencies;
    private FixerApi currencyApi;

    private CurrencyDatabase database;

    public CurrencyRepository(Application application, FixerApi currencyApi) {
        database = CurrencyDatabase.getInstance(application);
        currencyDao = database.currencyDao();
        getAllCurrencies = currencyDao.getAllCurrencies();
        this.currencyApi = currencyApi;
    }

    public void insert(CurrencyEntity currencyEntity) {
        new InsertCurrencyAsyncTask(currencyDao).execute(currencyEntity);
    }

    public LiveData<MainResource<CurrencyDTO>> fetchFromFixerApiService() {
        return LiveDataReactiveStreams.fromPublisher(
                currencyApi.getCurrencies(Constants.ACCESS_KEY)

                        //Instead of calling onError(error happens)
                        .onErrorReturn(throwable -> {
                            CurrencyDTO errorCurrency = new CurrencyDTO();
                            errorCurrency.setStatus(false);
                            return errorCurrency;
                        })

                        .map(currencyDTO -> {
                            if (!currencyDTO.getStatus()) {
                                return MainResource.error("Error with Fixer API", (CurrencyDTO) null);
                            }

                            CurrencyEntity currencyEntity = CurrencyMapper.apiToCurrencyMapper(currencyDTO);

                            insert(currencyEntity);
                            return MainResource.success(currencyDTO);

                        })
                        .subscribeOn(Schedulers.io())
        );
    }

    public LiveData<CurrencyEntity> getGetAllCurrencies() {
        return getAllCurrencies;
    }

    private static class InsertCurrencyAsyncTask extends AsyncTask<CurrencyEntity, Void, Void> {
        private CurrencyDao currencyDao;

        private InsertCurrencyAsyncTask(CurrencyDao currencyDao) {
            this.currencyDao = currencyDao;
        }

        @Override
        protected Void doInBackground(CurrencyEntity... currencies) {
            currencyDao.insert(currencies[0]);
            return null;
        }
    }
}