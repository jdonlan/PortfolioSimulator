package com.joshdonlan.portfoliosimulator.objetcs;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by jdonlan on 7/21/15.
 */
public class StockTask extends AsyncTask<String,Void,Stock[]> {

    public static final String TAG = "StockTask";

    private Context mContext;
    private boolean mActiveSearch;

    public interface StockListener{
        public void receiveStockData(Stock[] _stocks);
    }

    public StockTask(Context _context){
        this(_context, false);
    }

    public StockTask(Context _context, boolean _activeSearch) {
        mContext = _context;
        mActiveSearch = _activeSearch;
    }

    @Override
    protected Stock[] doInBackground(String... params) {
        Stock[] stocks = new Stock[params.length];

        int counter = 0;
        StockDataSource stockDataSource = new StockDataSource(mContext);
        stockDataSource.open();
        for(String symbol : params) {
            JSONObject jsonStock = null;
            try {
                String baseURL = "http://query.yahooapis.com/v1/public/yql";
                String yql = "select * from csv where url='http://download.finance.yahoo.com/d/quotes.csv?s=" + symbol + "&f=sl1d1t1c1ohgvp2&e=.csv' and columns='symbol,price,date,time,change,open,high,low,volume,chgpct'";
                String qs = URLEncoder.encode(yql, "UTF-8");
                URL queryURL = new URL(baseURL + "?q=" + qs + "&format=json");
                HttpURLConnection conn = (HttpURLConnection) queryURL.openConnection();
                String result = IOUtils.toString(conn.getInputStream());
                JSONObject jsonResult = new JSONObject(result);
                jsonStock = (jsonResult != null) ? jsonResult.getJSONObject("query").getJSONObject("results").getJSONObject("row") : null;
                Stock stock = null;
                if(jsonStock != null && jsonStock.getString("price").compareTo("N/A")!=0) {
                    stock = stockDataSource.updateStock(jsonStock);
                }
//                Stock stock = (jsonStock != null && jsonStock.getString("price").compareTo("N/A")!=0) ? new Stock(jsonStock) : null;
//                if(stock != null) AndroidIO.writeInternal(mContext,symbol+".dat",stock);
                stocks[counter] = stock;
                counter++;
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, "Error encoding API request.");
            } catch (IOException e) {
                Log.e(TAG, "Unable to connect to API.");
            } catch (JSONException e) {
                Log.e(TAG, "Could not convert API response to JSONObject.");
            }

        }
        stockDataSource.close();
        return stocks;
    }

    @Override
    protected void onPostExecute(Stock[] _stocks) {
        super.onPostExecute(_stocks);
        if(mContext instanceof StockListener && mActiveSearch){
            ((StockListener) mContext).receiveStockData(_stocks);
        }
    }
}
