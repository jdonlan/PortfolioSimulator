package com.joshdonlan.portfoliosimulator;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.joshdonlan.portfoliosimulator.fragments.PortfolioFragment;
import com.joshdonlan.portfoliosimulator.objetcs.StockDetailListener;


public class PortfolioActivity extends Activity implements StockDetailListener{

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
    public void loadStockDetail(String symbol) {
        //TODO: Start Intent to Load Stock Detail OR Populate Detail Fragment
    }
}
