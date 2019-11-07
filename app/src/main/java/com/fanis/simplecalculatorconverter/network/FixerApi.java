package com.fanis.simplecalculatorconverter.network;

import com.fanis.simplecalculatorconverter.models.CurrencyDTO;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FixerApi {

    @GET("/latest")
    Flowable<CurrencyDTO> getCurrencies(
            @Query("access_key") String accessKey
    );
}