package com.fanis.simplecalculatorconverter.converters;

import com.fanis.simplecalculatorconverter.database.converters.RatesConverter;
import com.fanis.simplecalculatorconverter.models.Rate;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class RateConvertersTest {

    @Test
    public void rateConvertersListToString() {
        List<Rate> actual = new ArrayList<>();
        actual.add(new Rate("AUD", 1.61));
        String expected = "[{\"currency\":\"AUD\",\"value\":1.61}]";

        Assert.assertEquals("Rate Converter List to String returns a String when a List is passed",expected, RatesConverter.ListToString(actual));

    }

    @Test
    public void rateConvertersStringToList() {
        List<Rate> expected = new ArrayList<>();
        expected.add(new Rate("AUD", 1.61));

        String actual = "[{\"currency\":\"AUD\",\"value\":1.61}]";

        Assert.assertEquals("Rate Converter String to List returns a List with currency AUD when AUD is passed as String","AUD", (RatesConverter.StringToList(actual)).get(0).getCurrency());
        Assert.assertEquals("Rate Converter String to List returns a List with value 1.61 when 1.61 is passed as String",1.61, (RatesConverter.StringToList(actual)).get(0).getValue(),0000);

    }
}
