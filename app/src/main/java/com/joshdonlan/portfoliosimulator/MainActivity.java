package com.joshdonlan.portfoliosimulator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.joshdonlan.portfoliosimulator.objetcs.StockTask;
import com.joshdonlan.utils.AndroidConnectivity;


public class MainActivity extends Activity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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








}
