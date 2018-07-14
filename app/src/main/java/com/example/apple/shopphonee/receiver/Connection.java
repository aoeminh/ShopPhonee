package com.example.apple.shopphonee.receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.activity.MainActivity;

public class Connection extends BroadcastReceiver {

    static final String TAG = "NETWORK_RECEIVER";

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (!isOnline(context)) {
            Toast.makeText(context,"No connection internet",Toast.LENGTH_SHORT).show();

        }
    }

    public boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());

    }


}

