package com.example.apple.shopphonee.activity;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.apple.shopphonee.database.SQLiteUtils;
import com.example.apple.shopphonee.model.Cart;
import com.example.apple.shopphonee.receiver.Connection;
import com.example.apple.shopphonee.utils.UtilsSharePref;

import okhttp3.internal.Util;

public abstract class BaseActivity extends AppCompatActivity {
    Connection receiver;

    SQLiteUtils sqLiteUtils = new SQLiteUtils(this);
SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id = getLayoutId();
        setContentView(id);
        initView();
        setAction();
        receiver = new Connection();
        registerReceiver(receiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);


    }

    abstract void initView();
    abstract int getLayoutId();
    abstract void setAction();


}
