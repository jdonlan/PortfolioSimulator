package com.joshdonlan.utils;

/**
 * Created by jdonlan on 7/21/15.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AndroidConnectivity {

    public static final int ONLINE = 0;
    public static final int WIFI = 1;
    public static final int MOBILE = 2;
    public static final int OFFLINE = -1;

    public static int getDataStatus(Context _context){
        return getDataStatus(_context, false);
    }

    public static int getDataStatus(Context _context, boolean _requesttype){
        ConnectivityManager cm = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm != null){
            NetworkInfo info = cm.getActiveNetworkInfo();

            //no network check
            if(info != null){
                //generic connected check
                if(info.isConnected() && !_requesttype){
                    return ONLINE; // connected
                }
                //specific connection type check
                if(info.getType() == ConnectivityManager.TYPE_WIFI){ //reliable high speed wifi
                    return WIFI; //wifi
                } else if (info.getType() == ConnectivityManager.TYPE_MOBILE){ //cell network internet connection
                    return MOBILE; //mobile
                }
                return ONLINE; //generic connection
            }
        }
        return OFFLINE; //not connected
    }

}
