package com.joshdonlan.portfoliosimulator.objetcs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jdonlan on 7/24/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_FILE = "stock.db";
    private static final int DATABASE_VERSION = 1;

    //SQL NAMES

    public static final String STOCK_TABLE = "stocks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SYMBOL = "symbol";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_HIGH = "high";
    public static final String COLUMN_LOW = "low";
    public static final String COLUMN_CHANGE = "change";
    public static final String COLUMN_OPEN = "open";
    public static final String COLUMN_VOLUME = "volume";
    public static final String COLUMN_PERCENT = "percent";

    //SQL COMMANDS

    private static final String CREATE_TABLE_STOCKS = "CREATE TABLE IF NOT EXISTS " + STOCK_TABLE + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + ", " +
            COLUMN_SYMBOL + " TEXT NOT NULL" + ", " +
            COLUMN_PRICE + " REAL NOT NULL" + ", " +
            COLUMN_DATE + " TEXT NOT NULL" + ", " +
            COLUMN_HIGH + " REAL NOT NULL" + ", " +
            COLUMN_LOW + " REAL NOT NULL" + ", " +
            COLUMN_CHANGE + " REAL NOT NULL" + ", " +
            COLUMN_OPEN + " REAL NOT NULL" + ", " +
            COLUMN_VOLUME + " INTEGER NOT NULL" + ", " +
            COLUMN_PERCENT + " TEXT NOT NULL" + ");";

    public DatabaseHelper(Context _context) {
        super(_context, DATABASE_FILE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STOCKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
