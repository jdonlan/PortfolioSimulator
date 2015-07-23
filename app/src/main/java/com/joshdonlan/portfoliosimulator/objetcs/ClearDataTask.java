package com.joshdonlan.portfoliosimulator.objetcs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.joshdonlan.portfoliosimulator.R;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by jdonlan on 7/23/15.
 */
public class ClearDataTask extends AsyncTask<Void, Integer, Void>{

    private Context mContext;
    private ProgressDialog mProgressDialog;
    private File[] mFiles;

    public ClearDataTask(Context _context){
        mContext = _context;
    };

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        File internal = mContext.getFilesDir();
        mFiles = internal.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (filename.endsWith(".dat")) return true;
                return false;
            }
        });

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(mContext.getResources().getString(R.string.clearDataMessage));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMax(mFiles.length);
        mProgressDialog.show();

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mProgressDialog.setProgress(values[0]);
    }

    @Override
    protected Void doInBackground(Void... params) {

        for(int i=0; i<mFiles.length; i++){
            onProgressUpdate(i);
            mFiles[i].delete();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        mProgressDialog.dismiss();
    }
}
