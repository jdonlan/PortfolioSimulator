package com.joshdonlan.portfoliosimulator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.joshdonlan.portfoliosimulator.objetcs.Stock;
import com.joshdonlan.portfoliosimulator.objetcs.StockTask;
import com.joshdonlan.utils.AndroidConnectivity;


public class MainActivity extends ActionBarActivity implements StockTask.StockListener{

    public static final String TAG = "MainActivity";

    private Toolbar toolbar;
    private boolean mSearchOpened = false;
    private MenuItem mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(AndroidConnectivity.getDataStatus(this) >= AndroidConnectivity.ONLINE) {

            //TODO: Replace with actual stock list
            String[] stocks = getResources().getStringArray(R.array.portfolio_temp);

            StockTask stockTask = new StockTask(this);
            stockTask.execute(stocks);
        }

        Button goPortfolio = (Button) findViewById(R.id.b_goportfolio);
        goPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadPortfolio = new Intent(v.getContext(), PortfolioActivity.class);
                startActivity(loadPortfolio);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(mSearchOpened) {
            toggleMenuSearch();
            return;
        }
        super.onBackPressed();
    }

    //MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_settings:
                break;
            case R.id.action_search:
                mSearch = item;
                toggleMenuSearch();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void toggleMenuSearch(){

        ActionBar action = getSupportActionBar();

        if(mSearchOpened){

            action.setDisplayShowCustomEnabled(false);
            action.setDisplayShowTitleEnabled(true);

            EditText searchField = (EditText)action.getCustomView().findViewById(R.id.et_search);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchField.getWindowToken(), 0);

            mSearch.setIcon(R.drawable.ic_search);
            mSearchOpened = false;

        } else {

            action.setDisplayShowCustomEnabled(true);
            action.setCustomView(R.layout.toolbar_search);
            action.setDisplayShowTitleEnabled(false);

            EditText searchField = (EditText)action.getCustomView().findViewById(R.id.et_search);

            searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        toggleMenuSearch();
                        doSearch(v.getText().toString());
                        return true;
                    }
                    return false;
                }
            });

            searchField.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(searchField, InputMethodManager.SHOW_IMPLICIT);

            mSearch.setIcon(R.drawable.ic_close);
            mSearchOpened = true;
        }
    }

    private void doSearch(String _query){
        StockTask stockTask = new StockTask(this,true);
        stockTask.execute(_query);
    }


    @Override
    public void receiveStockData(Stock[] _stocks) {
        Stock stock = _stocks[0];
        if(stock != null) {
            Intent loadDetails = new Intent(this, StockDetailActivity.class);
            Bundle extras = new Bundle();
            extras.putSerializable(StockDetailActivity.STOCK, stock);
            loadDetails.putExtras(extras);
            startActivity(loadDetails);
        } else {
            Toast.makeText(this, getResources().getString(R.string.toast_symbol_not_found),Toast.LENGTH_LONG)
                    .show();
        }
    }
}
