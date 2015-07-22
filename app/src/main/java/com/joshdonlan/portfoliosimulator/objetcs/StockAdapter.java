package com.joshdonlan.portfoliosimulator.objetcs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.joshdonlan.portfoliosimulator.R;

/**
 * Created by jdonlan on 7/22/15.
 */
public class StockAdapter extends BaseAdapter {

    private Stock[] mStocks;

    public StockAdapter(Stock[] _stocks){
        mStocks = _stocks;
    }

    @Override
    public int getCount() {
        return mStocks.length;
    }

    @Override
    public Stock getItem(int _position) {
        return mStocks[_position];
    }

    @Override
    public long getItemId(int _position) {
        return _position;
    }

    @Override
    public View getView(int _position, View _convertView, ViewGroup _parent) {

        ViewHolder holder;

        if(_convertView == null){
            _convertView = LayoutInflater.from(_parent.getContext()).inflate(R.layout.item_stock,_parent,false);
            holder = new ViewHolder(_convertView);
            _convertView.setTag(holder);
        } else {
            holder = (ViewHolder) _convertView.getTag();
        }

        holder.getSymbol().setText(getItem(_position).getSymbol());
        holder.getPrice().setText(getItem(_position).getPrice().toString());
        holder.getChange().setText(getItem(_position).getChange().toString());

        return _convertView;
    }

    static class ViewHolder{
        private TextView mSymbol;
        private TextView mPrice;
        private TextView mChange;

        ViewHolder(View v){
            mSymbol = (TextView) v.findViewById(R.id.tv_symbol);
            mPrice = (TextView) v.findViewById(R.id.tv_price);
            mChange = (TextView) v.findViewById(R.id.tv_change);
        }

        public TextView getSymbol(){ return mSymbol; };
        public TextView getPrice(){ return mPrice; };
        public TextView getChange(){ return mChange; };

    }
}