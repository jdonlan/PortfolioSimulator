package com.joshdonlan.portfoliosimulator.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joshdonlan.portfoliosimulator.R;
import com.joshdonlan.portfoliosimulator.objetcs.Stock;

/**
 * Created by jdonlan on 7/22/15.
 */
public class StockFragment extends Fragment{

    public static final String TAG = "StockFragment";
    public static final String STOCK = "stock";

    private Activity mContainingActivity;

    public static StockFragment newInstance(Stock _stock){
        StockFragment frag = new StockFragment();
        Bundle args = new Bundle();
        args.putSerializable(STOCK, _stock);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        mContainingActivity = _activity;
    }

    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState) {
        View view = _inflater.inflate(R.layout.fragment_stock_detail, _container, false);

        if(getArguments() != null) {
            Stock stock = (Stock) getArguments().getSerializable(STOCK);

            ((TextView) view.findViewById(R.id.tv_symbol)).setText(stock.getSymbol());
            ((TextView) view.findViewById(R.id.tv_price)).setText(stock.getPrice());
            ((TextView) view.findViewById(R.id.tv_date)).setText(stock.getDate());
            ((TextView) view.findViewById(R.id.tv_high)).setText(stock.getHigh());
            ((TextView) view.findViewById(R.id.tv_low)).setText(stock.getLow());
            ((TextView) view.findViewById(R.id.tv_change)).setText(stock.getChange());
            ((TextView) view.findViewById(R.id.tv_open)).setText(stock.getOpen());
            ((TextView) view.findViewById(R.id.tv_volume)).setText(stock.getVolume().toString());
            ((TextView) view.findViewById(R.id.tv_percent)).setText("("+stock.getPercent()+")");
        }

        return view;
    }
}
