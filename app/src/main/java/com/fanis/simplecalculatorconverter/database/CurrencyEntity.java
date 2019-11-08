package com.fanis.simplecalculatorconverter.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.fanis.simplecalculatorconverter.database.converters.RatesConverter;
import com.fanis.simplecalculatorconverter.models.Rate;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@Entity(tableName = "currency_table")
public class CurrencyEntity {

    @PrimaryKey
    @NotNull
    private String base;

    private String date;

    @TypeConverters(RatesConverter.class)
    private List<Rate> rates;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public CurrencyEntity(@NotNull String base, String date, List<Rate> rates) {
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "CurrencyEntity{" +
                "base='" + base + '\'' +
                ", date='" + date + '\'' +
                ", rates=" + rates +
                '}';
    }
}