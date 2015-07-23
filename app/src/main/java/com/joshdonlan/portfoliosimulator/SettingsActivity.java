package com.joshdonlan.portfoliosimulator;

import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.joshdonlan.portfoliosimulator.fragments.SettingsFragment;


public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        PreferenceFragment frag = new SettingsFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.f_settings,frag,SettingsFragment.TAG)
                .commit();
    }
}
