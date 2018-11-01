package ru.mrwinwon.poltindex.connection.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetConnection extends BroadcastReceiver {

    public static boolean connected = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork.isConnectedOrConnecting();

        connected = activeNetwork != null && activeNetwork.isConnected();
    }

    public boolean isConnected() {
        return connected;
    }
}

