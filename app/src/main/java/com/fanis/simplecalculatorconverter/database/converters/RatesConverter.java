package com.fanis.simplecalculatorconverter.database.converters;

import androidx.room.TypeConverter;

import com.fanis.simplecalculatorconverter.models.Rate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class RatesConverter {

    @TypeConverter
    public static List<Rate> StringToList(String rates) {
        if (rates == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Rate>>() {
        }.getType();
        return gson.fromJson(rates, type);
    }


    @TypeConverter
    public static String ListToString(List<Rate> rates) {
        if (rates == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Rate>>() {
        }.getType();
        return gson.toJson(rates, type);
    }
}
