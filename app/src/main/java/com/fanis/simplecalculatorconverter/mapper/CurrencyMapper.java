package com.fanis.simplecalculatorconverter.mapper;


import com.fanis.simplecalculatorconverter.database.CurrencyEntity;
import com.fanis.simplecalculatorconverter.models.CurrencyDTO;
import com.fanis.simplecalculatorconverter.models.Rate;
import com.fanis.simplecalculatorconverter.util.Miscellaneous;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurrencyMapper {

    public static CurrencyEntity apiToCurrencyMapper(CurrencyDTO apiResponse){

        List<Rate> rates = new ArrayList<>();
        for (Map.Entry<String, Double> entry : apiResponse.getRates().entrySet()) {
            rates.add(new Rate(entry.getKey(), roundDouble(entry.getValue(), 2)));
        }

        return new CurrencyEntity(apiResponse.getBase(),
                Miscellaneous.getDate(apiResponse.getTimestamp()),
                rates);
    }

    public static double roundDouble(double value, int places) {
        if (places < 0)
            return 0.00;

        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
}
