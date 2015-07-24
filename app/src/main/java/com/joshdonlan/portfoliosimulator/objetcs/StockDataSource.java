package com.joshdonlan.portfoliosimulator.objetcs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by jdonlan on 7/24/15.
 */
public class StockDataSource {

    public static final String TAG = "StockDataSource";

    private SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private String[] mAllColumns = {
            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_SYMBOL,
            DatabaseHelper.COLUMN_PRICE,
            DatabaseHelper.COLUMN_DATE,
            DatabaseHelper.COLUMN_HIGH,
            DatabaseHelper.COLUMN_LOW,
            DatabaseHelper.COLUMN_CHANGE,
            DatabaseHelper.COLUMN_OPEN,
            DatabaseHelper.COLUMN_VOLUME,
            DatabaseHelper.COLUMN_PERCENT
    };

    public StockDataSource(Context _context){
        mDBHelper = new DatabaseHelper(_context);
    }

    public void open(){
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close(){
        mDBHelper.close();
    }

    public Stock updateStock(JSONObject _stock){

        try {
            String symbol = _stock.getString("symbol");
            double price = _stock.getDouble("price");
            String date = _stock.getString("date") + " " + _stock.getString("time");
            double high = _stock.getDouble("high");
            double low = _stock.getDouble("low");
            double change = _stock.getDouble("change");
            double open = _stock.getDouble("open");
            int volume = _stock.getInt("volume");
            String percent = _stock.getString("chgpct");

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_SYMBOL, symbol);
            values.put(DatabaseHelper.COLUMN_PRICE, price);
            values.put(DatabaseHelper.COLUMN_CHANGE, change);
            values.put(DatabaseHelper.COLUMN_DATE, date);
            values.put(DatabaseHelper.COLUMN_HIGH, high);
            values.put(DatabaseHelper.COLUMN_LOW, low);
            values.put(DatabaseHelper.COLUMN_OPEN, open);
            values.put(DatabaseHelper.COLUMN_PERCENT,percent);
            values.put(DatabaseHelper.COLUMN_VOLUME, volume);

            String[] whereArgs = new String[]{symbol};
            Cursor cursor = mDB.query(DatabaseHelper.STOCK_TABLE, new String[]{DatabaseHelper.COLUMN_ID}, DatabaseHelper.COLUMN_SYMBOL + " = " + '?', whereArgs , null, null, null);
            long stockID = 0;
            if(cursor.getCount() == 0 || cursor == null) {
                stockID = mDB.insert(DatabaseHelper.STOCK_TABLE, null, values);
            } else {
                whereArgs = new String[]{""+cursor.getInt(0)};
                stockID = cursor.getInt(0);
                mDB.update(DatabaseHelper.STOCK_TABLE, values, DatabaseHelper.COLUMN_ID, whereArgs);
            }

            Stock stock = null;
            if(stockID > 0) {
                whereArgs = new String[]{"" + stockID};
                cursor = mDB.query(DatabaseHelper.STOCK_TABLE, mAllColumns, DatabaseHelper.COLUMN_ID + " = " + '?', whereArgs, null, null, null);
                cursor.moveToFirst();
                stock = cursorToStock(cursor);
            }
            return stock;

        } catch (Exception e) {
            Log.e(TAG, "Error parsing JSON stock data.");
        }

        return null;
    }

    public Stock[] readAllStocks(){
        Cursor cursor = mDB.query(DatabaseHelper.STOCK_TABLE, mAllColumns, null, null , null, null, null);

        Stock[] stocks = new Stock[cursor.getCount()];

        cursor.moveToFirst();
        int counter = 0;
        while(!cursor.isAfterLast()){
            Stock stock = cursorToStock(cursor);
            stocks[counter] = stock;
            counter++;
            cursor.moveToNext();
        }
        return stocks;
    }

    public Stock readStock(String _symbol){
        String[] whereArgs = {_symbol};
        Cursor cursor = mDB.query(DatabaseHelper.STOCK_TABLE, mAllColumns, DatabaseHelper.COLUMN_SYMBOL + " = " + '?', whereArgs , null, null, null);
        cursor.moveToFirst();
        Stock stock = cursorToStock(cursor);
        return stock;
    }

    public int deleteStock(String _symbol){
        String[] whereArgs = {_symbol};
        int affected = mDB.delete(DatabaseHelper.STOCK_TABLE, DatabaseHelper.COLUMN_SYMBOL + " = " + '?',whereArgs);
        return affected;
    }

    private Stock cursorToStock(Cursor _cursor){
        Stock stock = new Stock(
                _cursor.getString(1), //symbol
                _cursor.getDouble(2), //price
                _cursor.getString(3), //date
                _cursor.getDouble(4), //high
                _cursor.getDouble(5), //low
                _cursor.getDouble(6), //change
                _cursor.getDouble(7), //open
                _cursor.getInt(8), //volume
                _cursor.getString(9) //percent
        );

        return stock;
    }

}
