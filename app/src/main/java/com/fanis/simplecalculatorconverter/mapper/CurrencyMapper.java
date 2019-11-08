package com.fanis.simplecalculatorconverter.mapper;


import com.fanis.simplecalculatorconverter.database.CurrencyEntity;
import com.fanis.simplecalculatorconverter.models.CurrencyDTO;
import com.fanis.simplecalculatorconverter.models.Rate;
import com.fanis.simplecalculatorconverter.util.Miscellaneous;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurrencyMapper {

    public static CurrencyEntity apiToCurrencyMapper(CurrencyDTO apiResponse) {

        List<Rate> rates = new ArrayList<>();
        for (Map.Entry<String, Double> entry : apiResponse.getRates().entrySet()) {
            rates.add(new Rate(entry.getKey(), Miscellaneous.roundDouble(entry.getValue(), 2)));
        }

        return new CurrencyEntity(apiResponse.getBase(),
                Miscellaneous.getDate(apiResponse.getTimestamp()),
                rates);
    }
}