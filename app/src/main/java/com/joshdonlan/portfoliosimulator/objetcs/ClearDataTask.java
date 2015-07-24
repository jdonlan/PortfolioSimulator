package com.joshdonlan.portfoliosimulator.objetcs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.joshdonlan.portfoliosimulator.R;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by jdonlan on 7/23/15.
 */
public class ClearDataTask extends AsyncTask<Void, Integer, Void>{

    private static final String TAG = "ClearDataTask";
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private Stock[] mStocks;
    private StockDataSource mStockDataSource;


    public ClearDataTask(Context _context){
        mContext = _context;
    };

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mStockDataSource = new StockDataSource(mContext);
        mStockDataSource.open();;
        mStocks = mStockDataSource.readAllStocks();

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(mContext.getResources().getString(R.string.clearDataMessage));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMax(mStocks.length);
        mProgressDialog.show();

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mProgressDialog.setProgress(values[0]);
    }

    @Override
    protected Void doInBackground(Void... params) {

        for(int i=0; i <mStocks.length; i++){
            mStockDataSource.deleteStock(mStocks[i].getSymbol());
            Log.i(TAG, "Deleting" + mStocks[i].getSymbol());
            onProgressUpdate(i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mStockDataSource.close();
        mProgressDialog.dismiss();
    }
}
