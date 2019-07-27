package com.trackstockapp;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.trackstockapp.server.ConnectivityReceiver;

/**
 * Created by Hemant on 24-12-2017.
 */

public class AppApplication extends Application  {


    private final String TAG = AppApplication.class.getSimpleName();
    public static AppApplication instance = null;

    private  static Context mContext;

    public static synchronized AppApplication getInstance() {
        if (instance == null) {
            instance = new AppApplication();

        }
        return instance;
    }




    @Override
    public void onCreate() {
        super.onCreate();
        // Use like this:
//        try {
        mContext = this;
        instance = this;

    }

    public static boolean checkNetworkConnectivity(){
        ConnectivityManager cm =
                (ConnectivityManager)  mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        return isConnected;
    }



    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }


}
