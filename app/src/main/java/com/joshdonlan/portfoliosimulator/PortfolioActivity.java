package com.joshdonlan.portfoliosimulator;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.joshdonlan.portfoliosimulator.fragments.PortfolioFragment;
import com.joshdonlan.portfoliosimulator.fragments.StockFragment;
import com.joshdonlan.portfoliosimulator.objetcs.Stock;
import com.joshdonlan.portfoliosimulator.objetcs.StockDetailListener;
import com.joshdonlan.portfoliosimulator.objetcs.StockUpdateListener;


public class PortfolioActivity extends Activity implements StockDetailListener{

    private static final String TAG = "Portfolio Activity";
    public static final int DETAILREQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        if(savedInstanceState == null){
            Fragment portfolioFragment = PortfolioFragment.newInstance(R.array.portfolio_temp);
            getFragmentManager().beginTransaction()
                    .replace(R.id.f_portfoliolist,portfolioFragment,PortfolioFragment.TAG)
                    .commit();

        }
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);

        if(_resultCode == RESULT_OK && _requestCode == DETAILREQUEST){
            loadStockDetail((Stock) _data.getSerializableExtra(StockFragment.STOCK));
        }
    }

    //INTERFACE METHODS

    @Override
    public void loadStockDetail(Stock _stock) {
        if(findViewById(R.id.f_portfoliolist).getTag().toString().compareTo(getResources().getString(R.string.portfolio_landscape_tag)) == 0) {
            Log.d(TAG,"Loading landscape fragment detail");
            StockFragment detailFragment = (StockFragment) getFragmentManager().findFragmentByTag(StockFragment.TAG);
            if (detailFragment == null) {
                detailFragment = StockFragment.newInstance(_stock);
                getFragmentManager().beginTransaction()
                        .replace(R.id.f_stockdetail,detailFragment,StockFragment.TAG)
                        .commit();
            } else {
                if(detailFragment instanceof StockUpdateListener) {
                    ((StockUpdateListener) detailFragment).updateStockDetails(_stock);
                }
            }
        } else {
            Intent loadDetails = new Intent(this, StockDetailActivity.class);
            Bundle extras = new Bundle();
            extras.putSerializable(StockDetailActivity.STOCK, _stock);
            loadDetails.putExtras(extras);
            startActivityForResult(loadDetails,DETAILREQUEST);
        }
    }
}
