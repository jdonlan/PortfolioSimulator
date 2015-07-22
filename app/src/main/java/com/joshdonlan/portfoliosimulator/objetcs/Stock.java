package com.joshdonlan.portfoliosimulator.objetcs;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by jdonlan on 7/24/14.
 */
public class Stock implements Serializable{

    public static final String TAG = "Stock";

    private String mSymbol;
    private Double mPrice;
    private String mDate;
    private Double mHigh;
    private Double mLow;
    private Double mChange;
    private Double mOpen;
    private Integer mVolume;
    private String mPercent;

    public Stock(){}

    public Stock(String symbol, Double price, String date, Double high, Double low, Double change, Double open, Integer volume, String percent){
        mSymbol = symbol;
        mPrice = price;
        mDate = date;
        mHigh = high;
        mLow = low;
        mChange = change;
        mOpen = open;
        mVolume = volume;
        mPercent = percent;
    }

    public Stock(JSONObject stockData){
        try {
            mSymbol = stockData.getString("symbol");
            mPrice = stockData.getDouble("price");
            mDate = stockData.getString("date") + " " + stockData.getString("time");
            mHigh = stockData.getDouble("high");
            mLow = stockData.getDouble("low");
            mChange = stockData.getDouble("change");
            mOpen = stockData.getDouble("open");
            mVolume = stockData.getInt("volume");
            mPercent = stockData.getString("chgpct");
        } catch (Exception e) {
            Log.e(TAG, "Error updating display");
        }
    }

    public String getSymbol() {
        return mSymbol == null ? "N/A" : mSymbol;
    }

    public void setSymbol(String mSymbol) {
        this.mSymbol = mSymbol;
    }

    public Double getPrice() {
        return mPrice == null ? 0 : mPrice;
    }

    public void setPrice(Double mPrice) {
        this.mPrice = mPrice;
    }

    public String getDate() {
        return mDate == null ? "N/A" : mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public Double getHigh() {
        return mHigh == null ? 0 : mHigh;
    }

    public void setHigh(Double mHigh) {
        this.mHigh = mHigh;
    }

    public Double getLow() {
        return mLow == null ? 0 : mLow;
    }

    public void setLow(Double mLow) {
        this.mLow = mLow;
    }

    public Double getChange() {
        return mChange == null ? 0 : mChange;
    }

    public void setChange(Double mChange) {
        this.mChange = mChange;
    }

    public Double getOpen() {
        return mOpen == null ? 0 : mOpen;
    }

    public void setOpen(Double mOpen) {
        this.mOpen = mOpen;
    }

    public Integer getVolume() {
        return mVolume == null ? 0 : mVolume;
    }

    public void setVolume(Integer mVolume) {
        this.mVolume = mVolume;
    }

    public String getPercent() {
        return mPercent == null ? "0%" : mPercent;
    }

    public void setPercent(String mPercent) {
        this.mPercent = mPercent;
    }
}
