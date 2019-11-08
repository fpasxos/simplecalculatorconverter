package com.fanis.simplecalculatorconverter.util;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

public class Miscellaneous {

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time * 1000);
        return DateFormat.format("dd-MM-yyyy HH:mm:ss", cal).toString();

    }

    public static double roundDouble(double value, int places) {
        if (places < 0)
            return 0.00;

        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

}