package com.fanis.simplecalculatorconverter.models;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;

public class CurrencyDTO {

    @SerializedName("success")
    private boolean status;

    @SerializedName("timestamp")
    private int timestamp;

    @SerializedName("base")
    private String base;

    @SerializedName("date")
    private String date;

    @SerializedName("rates")
    private LinkedHashMap<String, Double> rates;

    public CurrencyDTO() {
    }

    public CurrencyDTO(boolean status, int timestamp, String base, String date, LinkedHashMap<String, Double> rates) {
        this.status = status;
        this.timestamp = timestamp;
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }



    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

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

    public LinkedHashMap<String, Double> getRates() {
        return rates;
    }

    public void setRates(LinkedHashMap<String, Double> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "CurrencyDTO{" +
                "status=" + status +
                ", timestamp=" + timestamp +
                ", base='" + base + '\'' +
                ", date='" + date + '\'' +
                ", rates=" + rates +
                '}';
    }
}
