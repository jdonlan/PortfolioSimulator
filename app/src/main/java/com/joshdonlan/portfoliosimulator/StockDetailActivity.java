package com.joshdonlan.portfoliosimulator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.joshdonlan.portfoliosimulator.fragments.StockFragment;
import com.joshdonlan.portfoliosimulator.objetcs.Stock;


public class StockDetailActivity extends Activity {

    public static final String TAG = "StockDetailActivity";
    public static final String STOCK = "stock";

    private Stock mStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);

        if(savedInstanceState == null){
            Intent loadingIntent = getIntent();
            Bundle extras = loadingIntent.getExtras();
            if(extras != null) {
                mStock = (Stock) extras.getSerializable(STOCK);
            }
            StockFragment frag = StockFragment.newInstance(mStock);
            getFragmentManager().beginTransaction()
                    .replace(R.id.f_stockdetail,frag,StockFragment.TAG)
                    .commit();
        } else {
            mStock = (Stock) savedInstanceState.getSerializable(STOCK);
        }
        if(mStock == null){
            Log.e(TAG,"Attempted to load stock detail with no stock provided.");
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(STOCK,mStock);
        super.onSaveInstanceState(outState);
    }
}
