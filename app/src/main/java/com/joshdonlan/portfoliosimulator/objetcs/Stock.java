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

    public String getPriceString() { return mPrice == null ? "0" : roundStringOrDouble(mPrice); }
    public String getHighString() { return mHigh == null ? "0" : roundStringOrDouble(mHigh); }
    public String getLowString() { return mLow == null ? "0" : roundStringOrDouble(mLow); }
    public String getChangeString() { return mChange == null ? "0" : roundStringOrDouble(mChange); }
    public String getOpenString() { return mOpen == null ? "0" : roundStringOrDouble(mOpen); }
    public String getPercentString() { return mPercent == null ? "0%" : roundStringOrDouble(mPercent)+"%"; }

    public String getSymbol() { return mSymbol == null ? "N/A" : mSymbol; }
    public String getDate() { return mDate == null ? "N/A" : mDate; }
    public Integer getVolume() { return mVolume == null ? 0 : mVolume; }
    public Double getPrice(){ return mPrice == null ? 0 : mPrice; }
    public Double getHigh(){ return mHigh == null ? 0 : mHigh; }
    public Double getLow(){ return mLow == null ? 0 : mLow; }
    public Double getChange(){ return mChange == null ? 0 : mChange; }
    public Double getOpen(){ return mOpen == null ? 0 : mOpen; }
    public String getPercent(){ return mPercent == null ? "0%" : mPercent; }


    private <T> String roundStringOrDouble(T _input){
        Double input;
        if(_input instanceof String){
            String inputString = (String) _input;
            input = Double.valueOf(inputString.substring(0,inputString.length()-1));
        } else {
            input = (Double) _input;
        }
        return String.format("%.2f", input);
    }
}
