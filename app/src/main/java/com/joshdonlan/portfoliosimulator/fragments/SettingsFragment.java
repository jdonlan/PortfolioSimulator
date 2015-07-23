package com.joshdonlan.portfoliosimulator.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.joshdonlan.portfoliosimulator.R;
import com.joshdonlan.portfoliosimulator.objetcs.ClearDataTask;

/**
 * Created by jdonlan on 7/23/15.
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    public static final String TAG = "SettingsFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Preference clearData = findPreference(getResources().getString(R.string.PREF_CLEARDATA));
        clearData.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getResources().getString(R.string.dialogue_clear_data))
                .setPositiveButton(getResources().getString(R.string.YES),clearDataListener)
                .setNegativeButton(getResources().getString(R.string.NO),clearDataListener)
                .show();

        return false;
    }

    DialogInterface.OnClickListener clearDataListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case AlertDialog.BUTTON_POSITIVE:
                    ClearDataTask clearDataTask = new ClearDataTask(getActivity());
                    clearDataTask.execute();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:
                    break;
            }

        }
    };
}
