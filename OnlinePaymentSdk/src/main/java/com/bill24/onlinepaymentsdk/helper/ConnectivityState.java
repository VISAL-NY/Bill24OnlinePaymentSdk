package com.bill24.onlinepaymentsdk.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityState extends BroadcastReceiver {

    private ConnectivityListener listener;
    private boolean wasResortConnection=true;
    public ConnectivityState(ConnectivityListener listener){
        this.listener=listener;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected=isConnectionConnected(context);
        if(listener!=null){
            listener.onNetworkConnectionChanged(isConnected,wasResortConnection);
        }
        wasResortConnection=isConnected;
    }

    private boolean isConnectionConnected(Context context){
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo=connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo!=null && activeNetworkInfo.isConnectedOrConnecting();
    }
    public interface ConnectivityListener{
        void onNetworkConnectionChanged(boolean isConnected,boolean wasResortConnection);
    }
}


