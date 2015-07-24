package com.joshdonlan.portfoliosimulator.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.joshdonlan.portfoliosimulator.R;
import com.joshdonlan.portfoliosimulator.StockDetailActivity;
import com.joshdonlan.portfoliosimulator.objetcs.DatabaseHelper;
import com.joshdonlan.portfoliosimulator.objetcs.Stock;
import com.joshdonlan.portfoliosimulator.objetcs.StockAdapter;
import com.joshdonlan.portfoliosimulator.objetcs.StockDataSource;
import com.joshdonlan.portfoliosimulator.objetcs.StockDetailListener;
import com.joshdonlan.utils.AndroidIO;

/**
 * Created by jdonlan on 7/22/15.
 */
public class PortfolioFragment extends Fragment {

    public static final String TAG = "PortfolioFragment";

    private Activity mContainingActivity;
    private static final String PORTFOLIO_ID = "portfolio_id";

    public static PortfolioFragment newInstance(int _portfolioID){
        PortfolioFragment frag = new PortfolioFragment();
        Bundle args = new Bundle();
        args.putInt(PORTFOLIO_ID,_portfolioID);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        if(_activity instanceof StockDetailListener){
            mContainingActivity = _activity;
        } else {
            throw new IllegalArgumentException("Containing activity must implement StockDetailListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolio,container,false);
        ListView stocklist = (ListView) view.findViewById(R.id.lv_stocks);

        Bundle args = getArguments();
        int portfolioID = (args != null) ? args.getInt(PORTFOLIO_ID) : 1;
        String[] symbols = getResources().getStringArray(portfolioID);
        Stock[] stocks = new Stock[symbols.length];
        int stocksRead = 0;
        StockDataSource stockDataSource = new StockDataSource(mContainingActivity);
        stockDataSource.open();
        for(String symbol : symbols){
//            Stock stock = (Stock) AndroidIO.readInternal(mContainingActivity,symbol+".dat");
            Stock stock = stockDataSource.readStock(symbol);
            stocks[stocksRead] = stock;
            stocksRead++;
        }
        stockDataSource.close();

        StockAdapter stockAdapter = new StockAdapter(mContainingActivity,stocks);
        stocklist.setAdapter(stockAdapter);
        stocklist.setOnItemClickListener(portfolioListListener);

        return view;
    }

    private AdapterView.OnItemClickListener portfolioListListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> _parent, View _view, int _position, long _id) {
            ((StockDetailListener) mContainingActivity).loadStockDetail((Stock) _parent.getItemAtPosition(_position));
        }
    };

}
