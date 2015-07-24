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
import com.joshdonlan.portfoliosimulator.objetcs.StockUpdateListener;

/**
 * Created by jdonlan on 7/22/15.
 */
public class StockFragment extends Fragment implements StockUpdateListener{

    public static final String TAG = "StockFragment";
    public static final String STOCK = "stock";

    private Activity mContainingActivity;
    private View mLayout;

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

        mLayout = view;
        if(getArguments() != null) {
            Stock stock = (Stock) getArguments().getSerializable(STOCK);
            updateStockDetails(stock);
        }

        return view;
    }

    @Override
    public void updateStockDetails(Stock _stock) {
        ((TextView) mLayout.findViewById(R.id.tv_symbol)).setText(_stock.getSymbol());
        ((TextView) mLayout.findViewById(R.id.tv_price)).setText(_stock.getPriceString());
        ((TextView) mLayout.findViewById(R.id.tv_date)).setText(_stock.getDate());
        ((TextView) mLayout.findViewById(R.id.tv_high)).setText(_stock.getHighString());
        ((TextView) mLayout.findViewById(R.id.tv_low)).setText(_stock.getLowString());
        ((TextView) mLayout.findViewById(R.id.tv_change)).setText(_stock.getChangeString());
        ((TextView) mLayout.findViewById(R.id.tv_open)).setText(_stock.getOpenString());
        ((TextView) mLayout.findViewById(R.id.tv_volume)).setText(_stock.getVolume().toString());
        ((TextView) mLayout.findViewById(R.id.tv_percent)).setText("("+_stock.getPercentString()+")");
    }
}
