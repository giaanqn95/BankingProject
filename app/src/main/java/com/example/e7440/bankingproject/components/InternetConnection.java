package com.example.e7440.bankingproject.components;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by buivu on 16/05/2017.
 */

public class InternetConnection {
    private static InternetConnection instance;

    private InternetConnection() {

    }

    public static InternetConnection getInstance() {
        if (instance == null) {
            instance = new InternetConnection();
        }
        return instance;
    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
