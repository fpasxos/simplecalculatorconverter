package com.fanis.simplecalculatorconverter.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CurrencyEntity currencyEntity);

    @Query("SELECT * FROM currency_table ORDER BY base ASC")
    LiveData<CurrencyEntity> getAllCurrencies();
}